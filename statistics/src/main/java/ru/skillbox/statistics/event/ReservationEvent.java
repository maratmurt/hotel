package ru.skillbox.statistics.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEvent extends BookingEvent {

    private Long userId;

    private LocalDate checkinDate;

    private LocalDate checkoutDate;

}
