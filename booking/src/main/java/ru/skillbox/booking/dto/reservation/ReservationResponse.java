package ru.skillbox.booking.dto.reservation;

import java.time.LocalDate;

public record ReservationResponse(
        LocalDate checkinDate,
        LocalDate checkoutDate,
        String hotelName,
        Integer roomNumber,
        Long userId
) {
}
