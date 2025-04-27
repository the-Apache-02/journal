package com.namo.journalApp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.namo.journalApp.entities.User;
import com.namo.journalApp.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{name}")
    public ResponseEntity<User> getByName(@PathVariable String name){
        User user=userService.findByName(name);
        if(user!=null){
            return new ResponseEntity<>(user,HttpStatus.FOUND);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{name}")
    public ResponseEntity<User> update(@PathVariable String name,@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        if(name==username){
            User updatedUser=userService.update(username, user);
        return updatedUser!=null?
        new ResponseEntity<>(updatedUser,HttpStatus.OK):
        
        new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name){
        userService.delete(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
