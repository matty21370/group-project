package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public String showItemsPage(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "catalog";
    }

    @GetMapping("/add")
    public String showAddItemPage() {
        return "addItem";
    }

    @PostMapping("/add")
    public String addItemSubmit(@ModelAttribute Item item, Model model) {
        model.addAttribute("item", item);
        itemRepository.save(item);
        return "items";
    }

}
