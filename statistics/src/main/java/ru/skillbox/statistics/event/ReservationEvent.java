package ru.skillbox.statistics.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationEvent extends BookingEvent {

    private LocalDate checkinDate;

    private LocalDate checkoutDate;

    public ReservationEvent(Long userId, LocalDate checkinDate, LocalDate checkoutDate) {
        super(userId);
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }
}
