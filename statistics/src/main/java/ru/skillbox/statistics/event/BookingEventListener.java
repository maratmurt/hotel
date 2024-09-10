package ru.skillbox.statistics.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingEventListener {

    @KafkaListener(
            topics = "registration-topic",
            groupId = "${app.kafka.group-id}",
            containerFactory = "registrationEventListenerContainerFactory"
    )
    public void listenToRegistration(
            @Payload RegistrationEvent event,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        log.info("Registration: {}", event);
    }

    @KafkaListener(
            topics = "reservation-topic",
            groupId = "${app.kafka.group-id}",
            containerFactory = "reservationEventListenerContainerFactory"
    )
    public void listenToReservation(
            @Payload ReservationEvent event,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        log.info("Reservation: {}", event);
    }

}
