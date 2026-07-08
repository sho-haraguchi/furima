package in.tech_camp.furima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.furima.service.ProductService;

@Controller
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping({ "/", "", "items" })
  public String showAllProduct(Model model) {
    model.addAttribute("items",productService.allProduct());
    return "items/index";
  }

}
