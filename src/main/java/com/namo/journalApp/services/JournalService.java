package com.namo.journalApp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.namo.journalApp.entities.Journal;
import com.namo.journalApp.entities.User;
import com.namo.journalApp.repositories.JournalRepo;
import com.namo.journalApp.repositories.UserRepo;
@Component
public class JournalService {
    @Autowired
    private JournalRepo journalRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Journal>getAll(){
        return journalRepo.findAll();
    }

    @Transactional
    public Journal create(Journal journal,String name){
        try {
            journal.setTime(LocalDateTime.now());
            User user=userRepo.findByUsername(name);
            journal=journalRepo.save(journal);
            //supppose their is an error occured in between this so the journal is saved but the user is not which leads to inconsistency
            //that's why we implement transaction
            user.getJournalList().add(journal);
            userRepo.save(user);
        } catch (Exception e) {
            System.out.println("error occured"+e);
            // TODO: handle exception
        }
        

        return journal;
    }

    public Optional<Journal>findById(ObjectId id){
        Optional<Journal> journal=journalRepo.findById(id);
        return journal;
    }

    public void deleteById(ObjectId id,String name){
        User user=userRepo.findByUsername(name);
        user.getJournalList().removeIf(entry->entry.getId().equals(id));
        System.out.println(user);
        userRepo.save(user);
        journalRepo.deleteById(id);
    }

    public Journal update(ObjectId id,Journal journal,String name){
        Journal oldJournal=journalRepo.findById(id).get();
        if(oldJournal!=null){
            oldJournal.setTitle(journal.getTitle()!=null && !journal.getTitle().equals("")?journal.getTitle():oldJournal.getTitle());
            oldJournal.setContent(journal.getContent()!=null && !journal.getContent().equals("")?journal.getContent():oldJournal.getContent());
            journalRepo.save(oldJournal);
        }
        return oldJournal;
    }
}
