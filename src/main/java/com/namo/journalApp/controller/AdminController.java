package com.namo.journalApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namo.journalApp.entities.User;

import com.namo.journalApp.services.UserService;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public ResponseEntity<List<User>>getAll(){
        List<User>list=userService.getAll();
        
        // if(!list.isEmpty()){
        //     List<JournalDTO>newList=list.stream().map(JournalDTO::new).collect(Collectors.toList());
        if(list!=null){
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }
            
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
