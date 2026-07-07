package in.tech_camp.furima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

  @GetMapping({ "/", "", "items" })
  public String showAllProduct() {
    return "items/index";
  }

}
