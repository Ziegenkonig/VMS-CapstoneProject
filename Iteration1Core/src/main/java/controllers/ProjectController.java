package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProjectController(){

  @GetMapping("/project")
  public String projectForm(Model model){

  }


  @PostMapping("/project")
  public String projectSubmit(){

  }
}
