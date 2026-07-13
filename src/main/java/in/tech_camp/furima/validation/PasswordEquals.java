package in.tech_camp.furima.validation;

import jakarta.validation.Payload;

import java.lang.annotation.*;
import jakarta.validation.Constraint;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordEqualsValidator.class)
public @interface PasswordEquals {

  String message() default "パスワードとパスワード（確認）が一致しません";

  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}