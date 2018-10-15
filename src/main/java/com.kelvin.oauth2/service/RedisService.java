package com.kelvin.oauth2.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisService {
    private static Map<String,JSONObject> map = new HashMap<String,JSONObject>();

    public void putValue(String key,JSONObject value){
        map.put(key,value);
    }

    public JSONObject getValue(String key){
        return map.get(key);
    }

    public Map<String,JSONObject> getMap(){
        return map;
    }

    public void remove(String key){
        map.remove(key);
    }
}
