package com.namo.journalApp.services;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.namo.journalApp.entities.User;
import com.namo.journalApp.repositories.UserRepo;

import lombok.extern.slf4j.Slf4j;
@Component
// @Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // private static final Logger logger=LoggerFactory.getLogger(UserService.class);
    @Autowired
    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public List<User>getAll(){
        return userRepo.findAll();
    }

    public User create(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User"));
            return userRepo.save(user);
        } catch (Exception e) {
            // TODO: handle exception
           // log.info("form usersercie",e);
        }
        return null;
    }

    public User findByName(String name) {
       return userRepo.findByUsername(name);
    }

    public User update(String name, User user) {
        User oldUser=userRepo.findByUsername(name);
        oldUser.setUsername(user.getUsername()!=null && user.getUsername().equals("")?user.getUsername():oldUser.getUsername());
        oldUser.setPassword(user.getPassword()!=null && user.getPassword().equals("")?user.getPassword():oldUser.getPassword());
        return userRepo.save(oldUser);
    }

    public void delete(String name) {
        User user=userRepo.findByUsername(name);
        userRepo.deleteById(user.getId());
    }
}
