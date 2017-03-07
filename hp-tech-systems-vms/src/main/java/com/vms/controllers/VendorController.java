
package com.vms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class VendorController {

  @GetMapping("/vendor")
  public String vendorForm(Model model) {
	  return null;
  }


  @PostMapping("/vendor")
  public String vendorSubmit() {
	  return null;
  }
}

