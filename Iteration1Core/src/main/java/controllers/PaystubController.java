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

  }


  @PostMapping("/paystub")
  public String paystubSubmit(){

  }
}
