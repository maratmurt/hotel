package ru.skillbox.booking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.mapper.NullAwareMapper;
import ru.skillbox.booking.model.Hotel;
import ru.skillbox.booking.repository.HotelRepository;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class HotelService implements CrudService<Hotel> {

    private final HotelRepository hotelRepository;

    private final NullAwareMapper nullAwareMapper;

    private static final String[] MESSAGES = {
            "Запрошенный отель не найден!"
    };

    @Override
    public List<Hotel> findAll(Integer page, Integer size) {
        return hotelRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[0]));
    }

    @Override
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel updatedHotel) {
        Hotel existingHotel = hotelRepository.findById(updatedHotel.getId()).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[0]));
        try {
            nullAwareMapper.copyProperties(existingHotel, updatedHotel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return hotelRepository.save(existingHotel);
    }

    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    public Hotel addRating(Long id, Integer newMark) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(MESSAGES[0]));
        double rating = hotel.getRating();
        int ratingsCount = hotel.getRatingsCount();

        if (ratingsCount == 0) {
            rating = Double.valueOf(newMark);
        } else {
            double totalRating = rating * ratingsCount;
            totalRating = totalRating - rating + newMark;
            rating = Double.parseDouble(new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US))
                    .format(totalRating / ratingsCount));
        }
        ratingsCount++;

        hotel.setRating(rating);
        hotel.setRatingsCount(ratingsCount);

        return hotelRepository.save(hotel);
    }

    public long count() {
        return hotelRepository.count();
    }

    public List<Hotel> findAllWithFilter(Integer page, Integer size, Specification<Hotel> specification) {
        return hotelRepository.findAll(specification, PageRequest.of(page, size)).toList();
    }

}
