package ru.skillbox.booking.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.mapper.NullAwareMapper;
import ru.skillbox.booking.model.Reservation;
import ru.skillbox.booking.model.Room;
import ru.skillbox.booking.model.User;
import ru.skillbox.booking.repository.ReservationRepository;
import ru.skillbox.booking.repository.RoomRepository;
import ru.skillbox.booking.repository.UserRepository;
import ru.skillbox.statistics.event.ReservationEvent;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService implements CrudService<Reservation> {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final NullAwareMapper nullAwareMapper;

    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    @Override
    public List<Reservation> findAll(Integer page, Integer size) {
        return reservationRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Запрошенная бронь не найдена!"));
    }

    @Override
    public Reservation create(Reservation reservation) {
        User user = userRepository.findById(reservation.getUser().getId()).orElseThrow(()->
                new EntityNotFoundException("Пользователь не найден!"));
        reservation.setUser(user);

        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(()->
                new EntityNotFoundException("Комната не найдена!"));
        reservation.setRoom(room);

        List<LocalDate> requestedDates = reservation.getCheckinDate()
                .datesUntil(reservation.getCheckoutDate())
                .toList();
        List<LocalDate> occupiedDates = room.getOccupiedDates();
        List<LocalDate> overlappingDates = requestedDates.stream()
                .filter(occupiedDates::contains)
                .toList();
        if (!overlappingDates.isEmpty()) {
            throw new ValidationException(MessageFormat.format(
                    "C {0} по {1} комната уже забронирована!",
                    overlappingDates.get(0),
                    overlappingDates.get(overlappingDates.size() - 1)));
        }
        occupiedDates.addAll(requestedDates);
        room.setOccupiedDates(occupiedDates);

        reservation = reservationRepository.save(reservation);
        ReservationEvent event = new ReservationEvent(reservation.getUser().getId(),
                reservation.getCheckinDate(), reservation.getCheckoutDate());
        kafkaTemplate.send("reservation-topic", UUID.randomUUID().toString(), event);

        return reservation;
    }

    @Override
    public Reservation update(Reservation updatedReservation) {
        Reservation existingReservation = reservationRepository.findById(updatedReservation.getId()).orElseThrow(()->
                new EntityNotFoundException("Запрошенная бронь не найдена!"));
        try {
            nullAwareMapper.copyProperties(existingReservation, updatedReservation);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return reservationRepository.save(existingReservation);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
