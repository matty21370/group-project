package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemsController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public String showItemsPage(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "catalog";
    }

    @GetMapping("/add")
    public String showAddItemPage(Model model) {
        //model.addAttribute("item", new Item());
        return "add-listing";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addItemSubmit(@ModelAttribute Item item, Model model) {
        model.addAttribute("item", item);
        itemRepository.save(item);
        return "add-listing";
    }

}
