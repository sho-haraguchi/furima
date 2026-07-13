package in.tech_camp.furima.validation;

import java.time.DateTimeException;
import java.time.LocalDate;

import in.tech_camp.furima.form.RegisterForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDate, RegisterForm> {

  @Override
  public boolean isValid(RegisterForm form, ConstraintValidatorContext context) {
    if (form.getBirthYear() == null || form.getBirthMonth() == null || form.getBirthDay() == null) {
      return true;
    }

    try {
      LocalDate.of(form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
      return true;
    } catch (DateTimeException e) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("birthDay")
          .addConstraintViolation();
      return false;
    }
  }

}
