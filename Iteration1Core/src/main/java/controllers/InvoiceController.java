package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class InvoiceController{

  @GetMapping("/invoice")
  public String invoiceForm(Model model){
    model.addAttribute("invoice", new Invoice());
    return "invoice";
  }


  @PostMapping("/invoice")
  public String invoiceSubmit(@ModelAttribute Invoice invoice){
    
  }
}
