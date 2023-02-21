package com.mocha.shopwebsite.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mocha.shopwebsite.data.Login;
@Controller
public class LoginController {
 @GetMapping("/login")
 public String showLogin() {
  return "login";
 }
 //Check for Credentials
 @PostMapping("/login")
 public String login(@ModelAttribute(name="loginForm") Login login, Model m) {
  String uname = login.getUsername();
  String pass = login.getPassword();
  if(uname.equals("Prabjot") && pass.equals("AmazingPrabjot")) {
   m.addAttribute("uname", uname);
   m.addAttribute("pass", pass);
   return "welcome";
  }
  m.addAttribute("error", "Incorrect Username & Password");
  return "login";
  
 }
}