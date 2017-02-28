package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InvoiceController{
  @RequestMapping("/sayInvoiceName")
  public String sayInvoiceName(@RequestParam(value="invName", required=false, defaultValue="NO INVOICE SUPPLIED")String invName, Model model){
    model.addAttribute("invName", invName);
    return "sayInvoiceName";
  }
}
