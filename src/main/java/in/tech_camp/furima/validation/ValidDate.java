package in.tech_camp.furima.validation;

import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface ValidDate {

  String message() default "存在する正しい日付を選択してください";

  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default{};

}
