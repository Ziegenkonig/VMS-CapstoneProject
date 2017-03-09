package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class VendorController{

  @GetMapping("/vendor")
  public String vendorForm(Model model){
    model.addAttribute("vendor", new Vendor());
    return "vendor";
  }


  @PostMapping("/vendor")
  public String vendorSubmit(@ModelAttribute Vendor vendor){
    //store in the service
    vendorService.create(vendor);
    return "vendorResult";
  }
}
