package ru.skillbox.booking.dto.room;

public record RoomRequest(
        String name,
        String description,
        Integer number,
        Double price,
        Integer capacity,
        Long hotelId
) {
}
