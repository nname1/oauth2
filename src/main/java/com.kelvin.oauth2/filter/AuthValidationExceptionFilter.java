package com.kelvin.oauth2.filter;

import com.kelvin.oauth2.service.AuthService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthValidationExceptionFilter implements Filter {

    protected static final Log logger = LogFactory.getLog(AuthValidationExceptionFilter.class);
    private String[] excludedPathArray;

    @Autowired
    AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludedUrls = filterConfig.getInitParameter("excludedUrl");
        if (!StringUtil.isNullOrEmpty(excludedUrls)) {
            excludedPathArray = excludedUrls.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean isExcluded=true;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        for (String path : excludedPathArray) {//判断是否在过滤url之外
            if (request.getServletPath().contains(path)) {
                isExcluded = false;
                break;
            }
        }
        if(isExcluded){
            try {
                Cookie[] cookies = request.getCookies();
                boolean found = false;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        found = true;
                        if (authService.validateCookie(cookie.getValue())) {
                            filterChain.doFilter(servletRequest, servletResponse);
                        } else {
                            response.setStatus(401);
                            response.sendError(401, "You are not authorized");
                        }
                    }
                }
                if (!found) {
                    response.setStatus(400);
                    response.sendError(400, "You are not logged in");
                }
            }catch(Exception e){
                if (response instanceof HttpServletResponse) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An expected error occured!");
                }
                logger.error(e.getMessage(), e);
            }
        }else{
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Access-Control-Max-Age, Authorization");
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
