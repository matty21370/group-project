package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetailsController {
    @GetMapping (value = {"/productdetails"})
    public String showProductDetails(){
        return "detail_product";
    }
    @Autowired
    Item item;
}
