package com.kelvin.oauth2.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class SessionService {

    public String getSessionId(){
        ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails())
                .getSessionId();
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        return sessionId;
    }

    public boolean validateSessionId(String sessionId){
        return true;
    }
}
