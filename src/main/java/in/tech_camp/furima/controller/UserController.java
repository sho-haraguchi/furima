package in.tech_camp.furima.controller;

import in.tech_camp.furima.form.RegisterForm;
import in.tech_camp.furima.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String userInsert(@Validated @ModelAttribute("registerForm") RegisterForm registerForm,
                             BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            errorSetPassForm(registerForm);
            return "users/sign_up";
        }

        if (!registerForm.getPassword().equals(registerForm.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.passwordConfirmation", "パスワードとパスワード（確認）が一致しません");
            errorSetPassForm(registerForm);
            return "users/sign_up";
        }

        if (userService.isEmailExist(registerForm.getEmail())) {
            result.rejectValue("email", "error.email", "このメールアドレスは既に登録されています");
            errorSetPassForm(registerForm);
            return "users/sign_up";
        }

        userService.userInsert(registerForm);

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
