package ru.skillbox.booking.dto.room;

import java.util.Date;
import java.util.List;

public record RoomResponse(
        Long id,
        String name,
        String description,
        Integer number,
        Double price,
        Integer capacity,
        List<Date> checkinDates
) {
}
