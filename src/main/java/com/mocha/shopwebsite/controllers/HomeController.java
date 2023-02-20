package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(value = {"/", "/home"})
    public String showHomePage(Model model) {
        Iterable<Item> allItems = itemRepository.findAll();
        model.addAttribute("items", allItems);

        return "index";
    }

    @GetMapping("/testadd")
    public @ResponseBody String addItem() {
        Item item = new Item();
        item.setName("Test");
        item.setPrice(999);
        item.setQuantity(12);
        item.setImage("gfrgdf");
        itemRepository.save(item);

        return "Added item!";
    }

}
