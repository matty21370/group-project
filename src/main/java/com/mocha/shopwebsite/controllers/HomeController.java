package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;
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

        boolean loggedIn = session.getAttribute("username") != null;

        if(loggedIn) {
            model.addAttribute("username", session.getAttribute("username"));
        }

        model.addAttribute("loggedIn", loggedIn);

        System.out.println(session.getAttribute("username"));

        return "home";
    }

    @GetMapping("/account")
    public String showAccountPage(Model model, HttpSession session) {
        boolean loggedIn = session.getAttribute("username") != null;

        if(!loggedIn) {
            //return "redirect:/login";
        }

        model.addAttribute("loggedIn", true);
        return "my_account";
    }
}
