package ru.skillbox.booking.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.booking.dto.hotel.HotelFilter;
import ru.skillbox.booking.dto.hotel.HotelListResponse;
import ru.skillbox.booking.dto.hotel.HotelRequest;
import ru.skillbox.booking.dto.hotel.HotelResponse;
import ru.skillbox.booking.model.Hotel;
import ru.skillbox.booking.model.HotelSpecification;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel toEntity(HotelRequest request);

    HotelResponse toResponse(Hotel hotel);

    default HotelListResponse toListResponse(List<Hotel> hotels) {
        return new HotelListResponse(hotels.stream().map(this::toResponse).toList(), null);
    }

    default Specification<Hotel> filterToSpecification(HotelFilter filter) {
        return HotelSpecification.byIds(filter.ids())
                .and(HotelSpecification.byNames(filter.names()))
                .and(HotelSpecification.byTitles(filter.titles()))
                .and(HotelSpecification.byCities(filter.cities()))
                .and(HotelSpecification.byAddresses(filter.addresses()))
                .and(HotelSpecification.byDistance(filter.minDistance(), filter.maxDistance()))
                .and(HotelSpecification.byRating(filter.minRating(), filter.maxRating()))
                .and(HotelSpecification.byRatingsCount(filter.minRatingsCount(), filter.maxRatingsCount()));
    }

}
