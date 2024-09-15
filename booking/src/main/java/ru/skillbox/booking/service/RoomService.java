package ru.skillbox.booking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.mapper.NullAwareMapper;
import ru.skillbox.booking.model.Hotel;
import ru.skillbox.booking.model.Room;
import ru.skillbox.booking.repository.HotelRepository;
import ru.skillbox.booking.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements CrudService<Room> {

    private final RoomRepository roomRepository;

    private final NullAwareMapper nullAwareMapper;

    private final HotelRepository hotelRepository;

    private static final String[] MESSAGES = {
            "Запрошенная комната не найдена!",
            "Отель не найден!"
    };

    @Override
    public List<Room> findAll(Integer page, Integer size) {
        return roomRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[0]));
    }

    @Override
    public Room create(Room room) {
        Hotel hotel = hotelRepository.findById(room.getHotel().getId()).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[1]));
        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    @Override
    public Room update(Room updatedRoom) {
        Room existingRoom = roomRepository.findById(updatedRoom.getId()).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[0]));
        try {
            nullAwareMapper.copyProperties(existingRoom, updatedRoom);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public List<Room> findAllWithFilter(Integer page, Integer size, Specification<Room> specification) {
        return roomRepository.findAll(specification, PageRequest.of(page, size)).toList();
    }
}
