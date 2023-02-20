package com.mocha.shopwebsite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.model.Model;

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
