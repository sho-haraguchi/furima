package in.tech_camp.furima.controller;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import in.tech_camp.furima.enums.*;
import in.tech_camp.furima.form.ProductForm;
import in.tech_camp.furima.security.CustomUserDetails;
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

  // 商品一覧表示機能
  @GetMapping({ "/", "", "items" })
  public String showAllProduct(Model model) {
    model.addAttribute("items", productService.allProduct());
    return "items/index";
  }

  // 商品出品機能
  @GetMapping("/items/new")
  public String showCreationForm(Model model) {
    model.addAttribute("productForm", new ProductForm());

    addEnumAttributesToModel(model);

    return "items/new";
  }

  @PostMapping("/items")
  public String createProduct(@ModelAttribute @Validated ProductForm productForm,
      BindingResult bindingResult,
      Model model,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    if (productForm.getImg() == null || productForm.getImg().isEmpty()) {
      bindingResult.rejectValue("img", "error.productForm", "出品画像を選択してください");
    }

    if (bindingResult.hasErrors()) {
      addEnumAttributesToModel(model);
      return "items/new";
    }

    Long loginUserId = userDetails.getUser().getId();

    try {
      productService.saveProduct(productForm, loginUserId);
    } catch (IOException e) {
      e.printStackTrace();

      bindingResult.rejectValue("img", "error.productForm", "画像の保存中にエラーが発生しました");
      addEnumAttributesToModel(model);
      return "items/new";
    }
    return "redirect:/";
  }

  private void addEnumAttributesToModel(Model model) {
    model.addAttribute("categories", Category.values());
    model.addAttribute("conditions", Condition.values());
    model.addAttribute("deliveryFees", DeliveryFee.values());
    model.addAttribute("prefectures", PrefectureType.values());
    model.addAttribute("untilDeliveries", UntilDelivery.values());
  }
}
