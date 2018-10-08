package com.kelvin.oauth2.service;

import com.kelvin.oauth2.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public Map<String,String> authorizeUser(UserInfo userInfo){
        Map<String, String> map = new HashMap<>();
        try {
            map = restTemplate.getForObject("http://localhost:8080/oauth/token?username=" + userInfo.getUsername() + "&password=" + userInfo.getPassword() + "&grant_type=password&scope=select&client_id=client_2&client_secret=123456", Map.class);
        }catch(Exception e){
            map.put("error",e.getMessage());
        }finally {
            System.out.println(map.toString());
        }
        return map;
    }
}
