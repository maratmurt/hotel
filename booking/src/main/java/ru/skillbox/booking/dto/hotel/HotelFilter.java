package ru.skillbox.booking.dto.hotel;

import java.util.List;

public record HotelFilter(
        List<Long> ids,
        List<String> names,
        List<String> titles,
        List<String> cities,
        List<String> addresses,
        Double minDistance,
        Double maxDistance,
        Double minRating,
        Double maxRating,
        Integer minRatingsCount,
        Integer maxRatingsCount
) {
}
