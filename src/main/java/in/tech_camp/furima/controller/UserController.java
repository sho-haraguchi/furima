package in.tech_camp.furima.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.furima.form.RegisterForm;
import in.tech_camp.furima.service.UserService;
import in.tech_camp.furima.validation.GroupOrder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/sign_in")
    public String login() {
        return "users/sign_in";
    }

    @GetMapping("/users/sign_up")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "users/sign_up";
    }

    @PostMapping("/users/sign_up")
    public String userInsert(@Validated(GroupOrder.class) @ModelAttribute("registerForm") RegisterForm registerForm,
            BindingResult result, HttpServletRequest request) {

        // 1. 基本的なアノテーションバリデーション（@NotBlankなど）のエラー判定
        if (result.hasErrors()) {
            errorSetPassForm(registerForm);
            return "users/sign_up";
        }

        // 2. サービス層でのビジネスロジックチェック
        Map<String, String> customErrors = userService.validateRegistration(registerForm);

        // エラーのMapが空でない（＝エラーがある）場合の処理
        if (!customErrors.isEmpty()) {
            customErrors.forEach((field, message) -> result.rejectValue(field, "error." + field, message));
            errorSetPassForm(registerForm);
            return "users/sign_up";
        }

        // 3. 登録処理（エラーがない場合のみ実行される）
        userService.userInsert(registerForm);

        // 4. ログイン処理
        try {
            request.login(registerForm.getEmail(), registerForm.getPassword());
        } catch (ServletException e) {
            return "redirect:/users/sign_in";
        }

        return "redirect:/";
    }

    private void errorSetPassForm(RegisterForm registerForm) {
        registerForm.setPassword("");
        registerForm.setPasswordConfirmation("");
    }

}
