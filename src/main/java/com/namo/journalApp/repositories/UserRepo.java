package com.namo.journalApp.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.namo.journalApp.entities.User;

public interface UserRepo extends MongoRepository<User,ObjectId>{
    public User findByUsername(String name);
}   
