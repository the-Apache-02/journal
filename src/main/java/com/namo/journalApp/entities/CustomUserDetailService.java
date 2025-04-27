package com.namo.journalApp.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.namo.journalApp.repositories.UserRepo;
@Component
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user=userRepo.findByUsername(username);
        List<GrantedAuthority>authorities=user
        .getRoles()
        .stream()
        .map(role->new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()))
        .collect(Collectors.toList());
        
        for(GrantedAuthority auth:authorities){
            System.out.println(auth.getAuthority());
        }
        if(user!=null){
            return new MyuserDetails(
                user.getUsername(),user.getId().toHexString(),user.getPassword(),authorities
            );
                    
        }
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
    //     User user=userRepo.findByUsername(username);
    //     if(user!=null){
    //         return org.springframework.security.core.userdetails.User.builder()
    //                 .username(user.getUsername())
    //                 .password(user.getPassword())
    //                 .roles(user.getRoles().toArray(new String[0]))
    //                 .build();
    //     }
    //     throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    // }

    
}
