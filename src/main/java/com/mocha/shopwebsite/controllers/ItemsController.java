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
    public String addItemSubmit(@ModelAttribute Item item, Model model, @RequestParam byte[] stringone, HttpSession session) {
        model.addAttribute("item", item);
        User user = userRepository.findUserByUsername((String) session.getAttribute("username"));
        int userId = user.getId();
        item.setUserId(userId);
        System.out.println(userId);
        //item.setImage(stringone);
       // item1.setName(string);
        itemRepository.save(item);
  
        return "redirect:/itemsdelete";
    }
    @GetMapping("/basket/{basketId}")
    public String viewBasket(@PathVariable("basketId") Long basketId, Model model, HttpSession session) {
        
        User user = userRepository.findUserByUsername((String) session.getAttribute("username")); 
        
        Integer userId = user.getId(); // This is getting the id associated with the username
        
        List<Item> items = basketRepository.findByUserId(userId);
       
        // This is getting the items and storing within list
        
        model.addAttribute("items", items); // adding to model
        
        return "checkout"; // return the basket
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
    public String updateItem(@RequestParam Long id, @RequestParam String string, @RequestParam String stringone) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setName(stringone);
            item.setImage(string);
            itemRepository.save(item);
        }
        return "redirect:/itemsdelete";
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GetMapping("/addtobasket")
    public String addToBasket(Model model, @RequestParam long id, HttpSession session) {
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

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "checkout";
    }

    @GetMapping("/records")
    @ResponseBody
    public String getNumberOfRecords() {
        long numOfRecords = itemRepository.count();
        return String.valueOf(numOfRecords);
    }

//    @GetMapping("/item/image/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//        Item item = itemRepository.findById(id).orElseThrow();
//        //byte[] imageBytes = item.getImage();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//    }
}
