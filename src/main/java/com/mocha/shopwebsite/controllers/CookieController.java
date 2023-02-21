package com.mocha.shopwebsite.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;

@Component
public class CookieController {

    public void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
        System.out.println("Added username cookie");
    }

    public String getUsername(@CookieValue(value = "username", defaultValue = "") String name) {
        return name;
    }

}
