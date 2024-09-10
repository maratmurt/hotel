package ru.skillbox.statistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.statistics.model.Item;
import ru.skillbox.statistics.repository.StatisticsRepository;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public Item create(Item item) {
        return statisticsRepository.save(item);
    }

}
