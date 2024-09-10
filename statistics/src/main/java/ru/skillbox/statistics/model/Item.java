package ru.skillbox.statistics.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking")
@Data
public class Item {

    @Id
    private String key;

    private String topic;

    private Long timestamp;

    private Object event;

}
