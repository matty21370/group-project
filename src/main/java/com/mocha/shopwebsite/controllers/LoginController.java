package com.mocha.shopwebsite.controllers;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mocha.shopwebsite.data.Login;
@Controller
public class LoginController {

 @GetMapping("/login")
 public String showLogin(Model model, HttpSession session) {
  boolean loggedIn = session.getAttribute("username") != null;

  if(loggedIn) {
   return "redirect:/home";
  }

  model.addAttribute("loginForm", new Login());
  return "sign-in";
 }
 //Check for Credentials
 @PostMapping("/login")
 public String login(@ModelAttribute(name="loginForm") Login login, Model m, HttpSession session) {
  String uname = login.getUsername();
  String pass = login.getPassword();
  if(uname.equals("Prabjot") && pass.equals("AmazingPrabjot")) {
   m.addAttribute("uname", uname);
   m.addAttribute("pass", pass);
   session.setAttribute("username", uname);
   return "redirect:home";
  }
  m.addAttribute("error", "Incorrect Username & Password");
  return "sign-in";
  
 }

 @GetMapping("/signout")
 public String signout(HttpSession session) {
  session.removeAttribute("username");
  return "redirect:/home";
 }
}