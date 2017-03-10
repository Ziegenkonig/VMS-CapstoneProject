package com.vms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaystubController {

  @GetMapping("/paystub")
  public String paystubForm(Model model) {
	  return null;
  }


  @PostMapping("/paystub")
  public String paystubSubmit() {
	  return null;
  }
  
}
