package com.stubhub.kelvin.oauth2.controller;

import com.stubhub.kelvin.oauth2.entity.UserInfo;
import com.stubhub.kelvin.oauth2.service.AuthService;
import com.stubhub.kelvin.oauth2.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class TestEndpoints {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    @CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "order id : " + id;
    }

    @CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
    @PostMapping("/login")
    public String login(HttpServletResponse response, @RequestBody UserInfo userInfo) {
        Map<String,String> authMap = userService.authorizeUser(userInfo);
        JSONObject json_token = new JSONObject();
        if(!authMap.containsKey("error")&&authMap.containsKey("access_token")){
            Cookie cookie = new Cookie("TEST","abc");
            response.addCookie(cookie);
            json_token.put("Authorization",authMap.get("token_type")+" "+authMap.get("access_token"));
            json_token.put("userName",userInfo.getUsername());
            return json_token.toString();
        }else{
            response.setStatus(400);
            json_token.put("error","login failed, please check the username and password");
            return json_token.toString();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
    @PostMapping("/validate")
    public boolean validate(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean valid = false;
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("TEST")) {
                    if (authService.validateCookie(cookie.getValue())) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    @PostMapping("/update")
    public UserInfo update(@RequestBody UserInfo userInfo) {
        return userInfo;
    }
}
