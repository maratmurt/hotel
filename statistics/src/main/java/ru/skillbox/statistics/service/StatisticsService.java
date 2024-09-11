package ru.skillbox.statistics.service;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.statistics.event.BookingEvent;
import ru.skillbox.statistics.event.ReservationEvent;
import ru.skillbox.statistics.model.Item;
import ru.skillbox.statistics.repository.StatisticsRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public Item create(Item item) {
        return statisticsRepository.save(item);
    }

    public void exportToCsv(String filePath) {
        List<Item> items = statisticsRepository.findAll();

        try(FileWriter fileWriter = new FileWriter(filePath);
            CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            String[] headers = {"Key", "Topic", "Timestamp", "User ID", "Check-in date", "Check-out date"};
            csvWriter.writeNext(headers);

            items.forEach(item -> {
                long timestampValue = item.getTimestamp();
                Instant instant = Instant.ofEpochMilli(timestampValue);
                String timestamp = instant.toString();
                String topic = item.getTopic();
                BookingEvent event = item.getDetails();
                String checkinDate = "";
                String checkoutDate = "";
                if (topic.equals("reservation-topic")) {
                    ReservationEvent reservationEvent = (ReservationEvent) event;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    checkinDate = reservationEvent.getCheckinDate().format(formatter);
                    checkoutDate = reservationEvent.getCheckoutDate().format(formatter);
                }
                String[] data = {
                        item.getKey(),
                        item.getTopic(),
                        timestamp,
                        event.getUserId().toString(),
                        checkinDate,
                        checkoutDate
                };
                csvWriter.writeNext(data);
                log.info(String.join(", ", data));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
