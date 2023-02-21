package com.mocha.shopwebsite.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {

    @GetMapping (value = {"/signup"})
    public String showSignInPage(){
        return "sign-up";
    }
    @GetMapping (value = "/createanaccount")
    public String showCreateAnAccountPage(){
        return "create-an-account";
    }
}
