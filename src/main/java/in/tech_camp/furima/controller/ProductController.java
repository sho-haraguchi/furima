package in.tech_camp.furima.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.furima.enums.*;
import in.tech_camp.furima.form.ProductForm;
import in.tech_camp.furima.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
            Model model) throws IOException {

        if (productForm.getImg() == null || productForm.getImg().isEmpty()) {
            bindingResult.rejectValue("img", "error.productForm", "出品画像を選択してください");
        }

        if (bindingResult.hasErrors()) {
            addEnumAttributesToModel(model);
            return "items/new";
        }

        Long loginUserId = 1L;
        productService.saveProduct(productForm, loginUserId);
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