package ru.skillbox.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.booking.dto.reservation.ReservationRequest;
import ru.skillbox.booking.dto.reservation.ReservationResponse;
import ru.skillbox.booking.model.Reservation;
import ru.skillbox.booking.model.Room;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "room", source = "roomId")
    Reservation toEntity(ReservationRequest request);

    @Mapping(target = "roomNumber", source = "room.number")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "hotelName", source = "room.hotel.name")
    ReservationResponse toResponse(Reservation reservation);

    default Room roomIdToRoom(Long roomId) {
        Room room = new Room();
        room.setId(roomId);
        return room;
    }

}
