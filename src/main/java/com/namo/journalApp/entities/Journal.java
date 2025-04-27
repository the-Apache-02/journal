package com.namo.journalApp.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "journalapp")
public class Journal {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime time;
}
