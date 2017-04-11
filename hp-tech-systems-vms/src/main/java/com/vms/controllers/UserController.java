package com.vms.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import security.ActiveUserStore;

@Controller
public class UserController {
     
    @Autowired
    ActiveUserStore activeUserStore;
    
    @GetMapping(value = "/users")
    public String getLoggedUsers(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }
}
