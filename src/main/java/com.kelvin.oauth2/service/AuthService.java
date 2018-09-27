package com.stubhub.kelvin.oauth2.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean validateCookie(String value){
        if(!value.equalsIgnoreCase("")){
            return true;
        }
        return false;
    }
}
