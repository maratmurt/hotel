package ru.skillbox.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.booking.dto.room.RoomFilter;
import ru.skillbox.booking.dto.room.RoomRequest;
import ru.skillbox.booking.dto.room.RoomResponse;
import ru.skillbox.booking.model.Hotel;
import ru.skillbox.booking.model.Room;
import ru.skillbox.booking.model.RoomSpecification;

import java.time.LocalDate;
import java.time.ZoneId;

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

    default Specification<Room> filterToSpecification(RoomFilter filter) {
        Specification<Room> specification = RoomSpecification.byIds(filter.ids())
                .and(RoomSpecification.byNames(filter.names()))
                .and(RoomSpecification.byPrice(filter.minPrice(), filter.maxPrice()))
                .and(RoomSpecification.byCapacity(filter.capacity()))
                .and(RoomSpecification.byHotelIds(filter.hotelIds()));

        if (filter.checkinDate() != null && filter.checkoutDate() != null) {
            LocalDate checkinDate = filter.checkinDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate checkoutDate = filter.checkoutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            specification = specification.and(RoomSpecification.byDates(checkinDate, checkoutDate));
        }

        return specification;
    }

}
