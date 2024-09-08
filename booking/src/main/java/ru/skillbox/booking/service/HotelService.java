package ru.skillbox.booking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.booking.mapper.NullAwareMapper;
import ru.skillbox.booking.model.Hotel;
import ru.skillbox.booking.repository.HotelRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService implements CrudService<Hotel> {

    private final HotelRepository hotelRepository;

    private final NullAwareMapper nullAwareMapper;

    @Override
    public List<Hotel> findAll(Integer page, Integer size) {
        return hotelRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Запрошенный отель не найден!"));
    }

    @Override
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel updatedHotel) {
        Hotel existingHotel = hotelRepository.findById(updatedHotel.getId()).orElseThrow(()->
                new EntityNotFoundException("Запрошенный отель не найден!"));
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

}
