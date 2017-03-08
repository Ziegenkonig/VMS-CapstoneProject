package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PaystubController{

  @GetMapping("/paystub")
  public String paystubForm(Model model){
    model.addAttribute("paystub", new Paystub());
    return "paystub";
  }


  @PostMapping("/paystub")
  public String paystubSubmit(@ModelAttribute Paystub paystub){
    //store in the service
    paystubService.create(paystub);
    return "paystubResult"
  }
}
