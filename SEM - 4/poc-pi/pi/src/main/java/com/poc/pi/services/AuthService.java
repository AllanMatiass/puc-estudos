package com.poc.pi.services;

import com.poc.pi.domain.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    public User createUser(UserRequest body){

        //logica boa

        return new User();

    }
}
