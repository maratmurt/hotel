package ru.skillbox.statistics.event;

import java.time.LocalDate;

public record ReservationEvent(Long userId, LocalDate checkinDate, LocalDate checkoutDate) {
}
