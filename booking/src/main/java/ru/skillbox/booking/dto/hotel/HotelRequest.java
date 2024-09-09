package ru.skillbox.booking.dto.hotel;

public record HotelRequest(
        String name,
        String title,
        String city,
        String address,
        Double distance
) {
}
