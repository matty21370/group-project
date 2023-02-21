package com.mocha.shopwebsite.controllers;

import com.mocha.shopwebsite.data.Item;
import com.mocha.shopwebsite.data.ItemRepository;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    
    
    @GetMapping("/itemsdelete")
    public String showItemsDeletePage(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "itemsdelete";
    }
    
    
    @GetMapping("/items/add")
    public String showAddItemPage(Model model) {
        model.addAttribute("item", new Item());
        return "addItem";
    }

    @RequestMapping(value = "/items/add", method = RequestMethod.POST)
    public String addItemSubmit(@ModelAttribute Item item, Model model) {
        model.addAttribute("item", item);
        itemRepository.save(item);
        return "addItem";
    }
    /*
    @GetMapping("/deleteItem")
	public String deleteItems(@RequestParam Long id) {
		itemRepository.deleteById(id);
		return "redirect:/list";
	
    
	@GetMapping({"/list", "/"})
	public ModelAndView getAllEmployees() {
		ModelAndView mav = new ModelAndView("list-employees");
		mav.addObject("items", itemRepository.findAll());
		return mav;
	}

	@GetMapping("/addEmployeeForm")
	public ModelAndView addEmployeeForm() {
		ModelAndView mav = new ModelAndView("add-employee-form");
		Item newEmployee = new Item();
		mav.addObject("employee", newEmployee);
		return mav;
	}*/
	
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
	        return "redirect:/items";
	    }
	  
	  @PostMapping("/items/update")
	  public String updateItem(@RequestParam Long id, @RequestParam String string) {
	      Item item = itemRepository.findById(id).orElse(null);
	      if (item != null) {
	          item.setImage(string);
	          itemRepository.save(item);
	      }
	      return "redirect:/items";
	  }
}

