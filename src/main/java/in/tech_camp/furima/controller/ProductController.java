package in.tech_camp.furima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.furima.service.ProductService;


@Controller
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  // 商品一覧表示
  @GetMapping({ "/", "", "items" })
  public String showAllProduct(Model model) {
    model.addAttribute("items", productService.allProduct());
    return "items/index";
  }

  // 商品詳細
  @GetMapping("/items/{id}")
  public String showProductDetail(@PathVariable Long id, Model model) {

    model.addAttribute("item", productService.selectByProductId(id));

    return "items/show";
  }

  // 商品削除
  @PostMapping("/items/delete/{id}")
  public String postMethodName(@PathVariable Long id, Model model) {

    productService.deleteByProductId(id);

    return "redirect:/";
  }

  // 商品編集
  @GetMapping("path")
  public String getMethodName(@RequestParam String param) {
      return new String();
  }
  

}
