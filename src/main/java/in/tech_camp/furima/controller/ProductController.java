package in.tech_camp.furima.controller;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.furima.dto.ProductDetailDto;
import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
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

    ProductDetailDto dto = productService.selectByProductId(id);

    model.addAttribute("item", dto);

    return "items/show";

  }

  // 商品削除
  @PostMapping("/items/delete/{id}")
  public String deleteProduct(@PathVariable Long id, Model model,
      @AuthenticationPrincipal CustomUserDetails loginUser) {

    try {
      productService.deleteByProductId(id, loginUser.getId());
    } catch (Exception e) {
      return "redirect:/items/" + id;
    }

    return "redirect:/";
  }

  // 商品編集
  @GetMapping("/items/edit/{id}")
  public String showProductEdit(@PathVariable Long id, Model model,
      @AuthenticationPrincipal CustomUserDetails loginUser) {

    ProductDetailDto dto = productService.selectByProductId(id);

    // その商品の情報を取得
    model.addAttribute("item", dto);
    try {
      model.addAttribute("productForm", productService.showEditProduct(dto, loginUser.getId()));
    } catch (Exception e) {
      return "redirect:/items/" + id;
    }

    addEnumAttributesToModel(model);

    return "items/edit";
  }

  // 商品編集
  @PostMapping("/items/edit/{id}")
  public String updateProduct(@PathVariable Long id, Model model,
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @ModelAttribute @Validated ProductForm productForm,
      BindingResult bindingResult) {

    ProductDetailDto dto = productService.selectByProductId(id);

    if (!dto.getUserId().equals(loginUser.getId())) {
      return "redirect:/items/" + id;
    }

    if (bindingResult.hasErrors()) {
      addEnumAttributesToModel(model);
      return "items/edit";
    }

    try {
      productService.updateByProductId(id, productForm, loginUser.getId(), dto.getImg());
    } catch (IOException e) {
      bindingResult.rejectValue("img", "error.productForm", "画像の保存中にエラーが発生しました");
      addEnumAttributesToModel(model);
      model.addAttribute("item", dto);
      return "items/edit";
    }

    return "redirect:/items/" + id;
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
    model.addAttribute("deliveryFees", DeliveryFeeType.values());
    model.addAttribute("prefectures", PrefectureType.values());
    model.addAttribute("untilDeliveries", UntilDelivery.values());
  }
}
