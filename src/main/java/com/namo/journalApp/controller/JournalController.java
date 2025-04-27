package com.namo.journalApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namo.journalApp.entities.Journal;
import com.namo.journalApp.entities.MyuserDetails;
import com.namo.journalApp.entities.User;
import com.namo.journalApp.services.JournalService;
import com.namo.journalApp.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Journal> create(@RequestBody Journal journal) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String name=authentication.getName();
        journal=journalService.create(journal,name);
        if(journal!=null){
            return new ResponseEntity<>(journal,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Journal>> getById(@PathVariable ObjectId id){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        String userid=((MyuserDetails) authentication.getPrincipal()).getId();
        System.out.println("user id is:: "+userid);
        User user=userService.findByName(username);
        List<Journal>list=user.getJournalList().stream().filter(entry->entry.getId().equals(id)).collect(Collectors.toList());
        if(!list.isEmpty()){
            Optional<Journal>journal=journalService.findById(id);
           if(journal.isPresent()){
            return new ResponseEntity<>(journal,HttpStatus.OK);
           }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journal> update(@PathVariable ObjectId id,@RequestBody Journal journal){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByName(username);
        List<Journal>list=user.getJournalList().stream().filter(entry->entry.getId().equals(id)).collect(Collectors.toList());
        Journal updatedJournal=null;
        if(!list.isEmpty()){
            updatedJournal=journalService.update(id, journal,username);
        }
        return updatedJournal!=null?
        new ResponseEntity<>(updatedJournal,HttpStatus.OK):
        
        new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByName(username);
        List<Journal>list=user.getJournalList().stream().filter(entry->entry.getId().equals(id)).collect(Collectors.toList());

        if(!list.isEmpty()){
            journalService.deleteById(id,username);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
