package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;
import com.mocha.shopwebsite.data.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ItemsController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/items")
    public String showItemsPage(Model model, HttpServletRequest request) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        System.out.println(request.getSession().getAttribute("Username"));

        return "catalog";
    }

    @GetMapping("/add")
    public String showAddItemPage(Model model, HttpSession session) {
        boolean loggedIn = session.getAttribute("username") != null;

        if(!loggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("item", new Item());
        return "add-listing";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addItemSubmit(@ModelAttribute Item item, Model model, HttpSession session) {
        model.addAttribute("item", item);
        //int userId = userRepository.fi
        //item.setUserId();
        itemRepository.save(item);
        return "redirect:/items";
    }

    @RequestMapping("/item")
    public String getItem(@RequestParam long id, Model model) {
        Optional<Item> foundItem = itemRepository.findById(id);

        if(foundItem.isPresent()) {
            Item item = foundItem.get();
            model.addAttribute("item", item);
            System.out.println(item.getName());
        }

        return "detail_product";
    }

    @GetMapping("/itemsdelete")
    public String showItemsDeletePage(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "itemsDelete";
    }

    @PostMapping("/saveItem")
    public String saveEmployee(@ModelAttribute Item items) {
        itemRepository.save(items);
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("add-item-form");
        Item items = itemRepository.findById(id).get();
        mav.addObject("items", items);
        return mav;
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return "redirect:/itemsdelete";
    }

    @PostMapping("/update")
    public String updateItem(@RequestParam Long id, @RequestParam String string) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setImage(string);
            itemRepository.save(item);
        }
        return "redirect:/items";
    }

    @GetMapping("/checkout")
    public String showCheckout() {
        return "checkout";
    }

}
