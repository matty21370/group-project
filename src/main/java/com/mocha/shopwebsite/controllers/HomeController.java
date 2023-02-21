package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CookieController cookieController;

    @GetMapping(value = {"/", "/home"})
    public String showHomePage(Model model) {
        Iterable<Item> allItems = itemRepository.findAll();
        model.addAttribute("items", allItems);

        if(!cookieController.getUsername("username").equals("")) {
            model.addAttribute("username", cookieController.getUsername("username"));
        }

        return "home";
    }
}
