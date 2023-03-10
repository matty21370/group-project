package com.mocha.shopwebsite.controllers;
import com.mocha.shopwebsite.data.*;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@Controller
public class ItemsController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/items")
    public String showItemsPage(Model model, HttpSession session) {
        Iterable<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);
        model.addAttribute("loggedIn", Helper.getInstance().isLoggedIn(session));

        return "catalog";
    }

    @GetMapping("/add")
    public String showAddItemPage(Model model, HttpSession session) {
        boolean loggedIn = session.getAttribute("username") != null;

        if(!loggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("item", new Item());
        model.addAttribute("loggedIn", true);
        return "add-listing";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addItemSubmit(@ModelAttribute Item item, Model model, HttpSession session) {
        model.addAttribute("item", item);
        User user = userRepository.findUserByUsername((String) session.getAttribute("username"));
        int userId = user.getId();
        item.setUserId(userId);
        System.out.println(userId);
        itemRepository.save(item);
  
        return "redirect:/items";
    }

    @RequestMapping("/item")
    public String getItem(@RequestParam long id, Model model, HttpSession session) {
        Optional<Item> foundItem = itemRepository.findById(id);

        if(foundItem.isPresent()) {
            Item item = foundItem.get();
            model.addAttribute("item", item);
            System.out.println(item.getName());
        }

        model.addAttribute("loggedIn", Helper.getInstance().isLoggedIn(session));

        return "detail_product";
    }

    @GetMapping("/itemsDelete")
    public String showItemsDeletePage(Model model, HttpSession session) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        model.addAttribute("loggedIn", Helper.getInstance().isLoggedIn(session));

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
    public String updateItem(@RequestParam Long id, @RequestParam String string, @RequestParam String stringone) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setName(stringone);
            item.setImage(string);
            itemRepository.save(item);
        }
        return "redirect:/items";
    }

    @GetMapping("/addtobasket")
    public String addToBasket(@RequestParam long id, HttpSession session) {
        boolean loggedIn = session.getAttribute("username") != null;

        if(!loggedIn) {
            return "redirect:/login";
        }

        Basket basket = new Basket();
        User user = userRepository.findUserByUsername((String) session.getAttribute("username"));
        basket.setItemId(id);
        basket.setUserId(user.getId());

        System.out.println(basket.getItemId());
        basketRepository.save(basket);

        return "redirect:/items";
    }

    @GetMapping("/records")
    @ResponseBody
    public String getNumberOfRecords() {
        long numOfRecords = itemRepository.count();
        return String.valueOf(numOfRecords);
    }

    @GetMapping("/checkout")
    public String viewBasket(Model model, HttpSession session) {

        User user = userRepository.findUserByUsername((String) session.getAttribute("username"));

        Integer userId = user.getId(); // This is getting the id associated with the username

        List<Basket> orders = basketRepository.findByUserId(userId);
        List<Item> items = new ArrayList<>();

        for(Basket basket : orders) {
            long itemId = basket.getItemId();
            Optional<Item> item = itemRepository.findById(itemId);
            item.ifPresent(items::add);
        }

        model.addAttribute("items", items); // adding to model
        model.addAttribute("loggedIn", Helper.getInstance().isLoggedIn(session));

        return "checkout"; // return the basket
    }

    @PostMapping("/checkoutone")
    public String deleteItemm(@RequestParam Integer id) {
        basketRepository.deleteById(id);
        return "checkout";
    }

    @PostMapping("/checkingout")
    public String checkingout() {
    	basketRepository.deleteAll();
        return "checkingout";
    }
}
