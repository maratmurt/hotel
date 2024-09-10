package ru.skillbox.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.statistics.model.Item;

@Repository
public interface StatisticsRepository extends MongoRepository<Item, String> {
}
