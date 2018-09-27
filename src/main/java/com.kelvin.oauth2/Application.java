package com.stubhub.kelvin.oauth2;

import com.stubhub.kelvin.oauth2.filter.AuthValidationExceptionFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
public class Application {

    public static void main(String[] args){
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @Bean
    public FilterRegistrationBean myFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthValidationExceptionFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("excludedUrl", "/login,/validate,/oauth/token,/order");
        registration.setName("authValidationExceptionFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
