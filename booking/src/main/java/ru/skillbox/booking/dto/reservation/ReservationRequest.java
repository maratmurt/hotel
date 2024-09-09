package ru.skillbox.booking.dto.reservation;

import java.time.LocalDate;

public record ReservationRequest(
        LocalDate checkinDate,
        LocalDate checkoutDate,
        Long roomId
) {
}
