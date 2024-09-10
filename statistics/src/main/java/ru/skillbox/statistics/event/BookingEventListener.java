package ru.skillbox.statistics.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.skillbox.statistics.model.Item;
import ru.skillbox.statistics.service.StatisticsService;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookingEventListener {

    private final StatisticsService statisticsService;

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
        log.info("Received message: {}", event);
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);

        Item item = new Item();
        item.setKey(key);
        item.setTopic(topic);
        item.setTimestamp(timestamp);
        item.setEvent(event);

        statisticsService.create(item);
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
        log.info("Received message: {}", event);
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);

        Item item = new Item();
        item.setKey(key);
        item.setTopic(topic);
        item.setTimestamp(timestamp);
        item.setEvent(event);

        statisticsService.create(item);
    }

}
