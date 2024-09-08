package ru.skillbox.booking.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.booking.dto.hotel.HotelListResponse;
import ru.skillbox.booking.dto.hotel.HotelRequest;
import ru.skillbox.booking.dto.hotel.HotelResponse;
import ru.skillbox.booking.model.Hotel;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel toEntity(HotelRequest request);

    HotelResponse toResponse(Hotel hotel);

    default HotelListResponse toListResponse(List<Hotel> hotels) {
        return new HotelListResponse(hotels.stream().map(this::toResponse).toList());
    }

}
