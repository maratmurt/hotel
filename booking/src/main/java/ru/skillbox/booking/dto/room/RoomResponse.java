package ru.skillbox.booking.dto.room;

public record RoomResponse(
        Long id,
        String name,
        String description,
        Integer number,
        Double price,
        Integer capacity
) {
}
