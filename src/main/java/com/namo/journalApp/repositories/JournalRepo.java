package com.namo.journalApp.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.namo.journalApp.entities.Journal;

public interface JournalRepo extends MongoRepository<Journal,ObjectId>{

}
