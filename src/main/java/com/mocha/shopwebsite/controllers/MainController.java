package com.mocha.shopwebsite.controllers;
import com.mocha.shopwebsite.data.User;
import com.mocha.shopwebsite.data.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	private UserRepository userRepository;
	
	  @PostMapping(path="/add") // Map ONLY POST Requests
	
	  public @ResponseBody String addNewUser 
			(@RequestParam String name, 
			 @RequestParam Integer id) 
	  {
		  
	    User n = new User();
	    n.setFirstName(name);
	    n.setId(id);
	    userRepository.save(n);
	    return "Saved";
	  }
	
	  @GetMapping(path="/all")
	  public @ResponseBody Iterable<User> getAllUsers() {
	    // This returns a JSON or XML with the users
	   return userRepository.findAll();
	  
	  }
	  

}


