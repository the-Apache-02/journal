package com.namo.journalApp.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;

@Data
@Document(collection ="users")
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    
    private LocalDateTime localDateTime=LocalDateTime.now();

    @DBRef
    private List<Journal>journalList=new ArrayList<>();

    private List<String>roles;
}
