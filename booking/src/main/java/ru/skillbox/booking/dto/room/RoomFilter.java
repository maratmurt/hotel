package ru.skillbox.booking.dto.room;

import java.util.Date;
import java.util.List;

public record RoomFilter(
        List<Long> ids,
        List<String> names,
        Double minPrice,
        Double maxPrice,
        Integer capacity,
        Date checkinDate,
        Date checkoutDate,
        List<Long> hotelIds
) {
}
