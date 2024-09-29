package com.plume.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CookieUtils {

    public static void addCookie(String cookieKey, String cookieValue, int cookieExpireIn, String domain, boolean httpOnly, boolean isSecure){

        HttpServletResponse response = getResponse();

        Cookie cookie = new Cookie(cookieKey, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(cookieExpireIn);
        //cookie.setDomain(domain);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(isSecure);
        response.addCookie(cookie);
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getResponse();
    }
}
