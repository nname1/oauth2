package com.kelvin.oauth2.controller;

import com.kelvin.oauth2.entity.UserInfo;
import com.kelvin.oauth2.service.AuthService;
import com.kelvin.oauth2.service.RedisService;
import com.kelvin.oauth2.service.SessionService;
import com.kelvin.oauth2.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
public class TestEndpoints {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    SessionService sessionService;

    @Autowired
    RedisService redisService;

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "order id : " + id;
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse response, @RequestBody UserInfo userInfo) {
        Map<String,String> authMap = userService.authorizeUser(userInfo);
        JSONObject json_token = new JSONObject();
        if(!authMap.containsKey("error")&&authMap.containsKey("access_token")){
            Cookie cookie = new Cookie("JSESSIONID",sessionService.getSessionId());
            cookie.setMaxAge(50);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            json_token.put("Authorization",authMap.get("token_type")+" "+authMap.get("access_token"));
            json_token.put("userName",userInfo.getUsername());
            json_token.put("authMap",authMap.toString());
            json_token.put("JSESSIONID",sessionService.getSessionId());
            redisService.putValue(request.getHeader("state"),json_token);
            System.out.println(json_token.toString());
            return json_token.toString();
        }else{
            response.setStatus(400);
            json_token.put("error","login failed, please check the username and password");
            return json_token.toString();
        }
    }

    @GetMapping("/validate")
    public boolean validate(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        boolean valid = false;
        String jSesionCookie = "",key="";
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    jSesionCookie = cookie.getValue();
                    if (authService.validateCookie(jSesionCookie)) {
                        valid = true;
                    }
                }
            }
        }

        if(request.getHeader("state")!=null&&!request.getHeader("state").isEmpty()){
            key=request.getHeader("state");
        }else if(!jSesionCookie.isEmpty()){
            key=jSesionCookie;
        }

        if(!key.isEmpty()&&redisService.getMap().containsKey(key)){
            JSONObject jsonObject = redisService.getValue(key);
            response.setHeader("Authorization",jsonObject.getString("Authorization"));
            Cookie cookie = new Cookie("JSESSIONID",jsonObject.getString("JSESSIONID"));
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            response.setHeader("UserName",jsonObject.getString("userName"));
            //redisService.remove(request.getHeader("state"));
        }else{
            response.setHeader("State",sessionService.getSessionId());
        }
        /*if(!valid){
            response.setStatus(302);
            response.setHeader("Location","http://localhost:3001/login");
        }*/
        return valid;
    }

    @PostMapping("/update")
    public UserInfo update(@RequestBody UserInfo userInfo) {
        return userInfo;
    }
}
