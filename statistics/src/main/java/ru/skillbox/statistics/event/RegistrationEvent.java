package ru.skillbox.statistics.event;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegistrationEvent extends BookingEvent {

    public RegistrationEvent(Long userId) {
        super(userId);
    }

}
