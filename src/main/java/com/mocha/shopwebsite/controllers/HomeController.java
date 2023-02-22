package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;
import com.mocha.shopwebsite.data.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(value = {"/", "/home"})
    public String showHomePage(Model model, HttpSession session) {
        Iterable<Item> allItems = itemRepository.findAll();
        model.addAttribute("items", allItems);

        System.out.println(session.getAttribute("username"));

        return "home";
    }
}
