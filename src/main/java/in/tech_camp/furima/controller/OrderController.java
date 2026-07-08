package in.tech_camp.furima.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.furima.dto.OrderDto;
import in.tech_camp.furima.entity.ProductEntity;
import in.tech_camp.furima.service.OrderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequiredArgsConstructor
public class OrderController {
  
  private final OrderService orderService;

  @Value("${payjp.public.key}")
  private String payjpPublicKey;

  // 購入画面の表示
  @GetMapping("/items/{productId}/orders")
  public String showOrderPage(@PathVariable Long productId, 
                              @AuthenticationPrincipal CustomUserDetail userDetail,
                              @ModelAttribute OrderDto orderDto, Model model) {

    // ログアウト状態の場合は、商品購入ページに遷移しようとすると、商品の販売状況に関わらずログインページに遷移すること。
    if (userDetail == null) {
      return "redirect:/users/sign_in";
    }

    Long currentUserId = userDetail.getUser().getId();
    ProductEntity product = orderService.findById(productId);

    // // ログイン状態の場合でも、自身が出品した商品の商品購入ページに遷移しようとすると、商品の販売状況に関わらずトップページに遷移すること。
    if (product.getUserid().equals(currentUserId)) {
      return "redirect:/";
    }

    // ログイン状態の場合でも、自身が出品していない売却済み商品の商品購入ページへ遷移しようとすると、トップページに遷移すること。
    if (orderService.isSoldout(productId)) {
      return "redirect:/";
    }

    model.addAttribute("product", product);
    model.addAttribute("payjpPublicKey", payjpPublicKey);
    
    return "orders/index";
  }
  

  // 購入処理
  @PostMapping("/items/{productId}/orders")
  public String buy(@PathVariable Long productId,
                    @AuthenticationPrincipal CustomUserDetail userDetail,
                    @Validated(OrderDto.ValidationOrder.class) @ModelAttribute OrderDto orderDto,
                    BindingResult bindingResult,
                    Model model) {

    // エラーハンドリングができること（入力に問題がある状態で「購入」ボタンが押された場合、情報は受け入れられず、購入ページでエラーメッセージが表示されること）。
    if (bindingResult.hasErrors()) {
      ProductEntity product = orderService.findById(productId);
      model.addAttribute("product", product);
      model.addAttribute("payjpPublicKey", payjpPublicKey); // 再表示時も鍵が必要なので

      return "orders/index";
    }

    // OrderServiceの購入・決済処理
    Long currentUserId = userDetail.getUser().getId();
    orderService.createOrder(productId, currentUserId, orderDto);

    return "redirect:/";
  }
}
