package ru.skillbox.booking.dto.hotel;

public record HotelResponse(
        Long id,
        String name,
        String city,
        Double distance,
        Double rating,
        Integer ratingsCount
) {
}
