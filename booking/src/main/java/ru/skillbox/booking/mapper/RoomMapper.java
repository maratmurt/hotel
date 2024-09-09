package ru.skillbox.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.booking.dto.room.RoomRequest;
import ru.skillbox.booking.dto.room.RoomResponse;
import ru.skillbox.booking.model.Hotel;
import ru.skillbox.booking.model.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "hotel", source = "hotelId")
    Room toEntity(RoomRequest request);

    RoomResponse toResponse(Room room);

    default Hotel hotelIdToHotel(Long hotelId) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        return hotel;
    }

}
