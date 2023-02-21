package com.mocha.shopwebsite.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @GetMapping(value = {"/checkout"})
        public String showCheckoutPage(){
            return "checkout";
    }
    @GetMapping (value = {"/orderconfirmation"})
    public String showOrderconfirmationPage(){
        return "order-confirmation";
    }
}
