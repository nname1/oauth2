package com.kelvin.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    SessionService sessionService;

    public boolean validateCookie(String value){
        if(sessionService.validateSessionId(value)){
            return true;
        }
        return false;
    }
}
