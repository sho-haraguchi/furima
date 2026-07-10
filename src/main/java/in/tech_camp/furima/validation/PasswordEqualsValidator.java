package in.tech_camp.furima.validation;

import in.tech_camp.furima.form.RegisterForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals, RegisterForm>{

  @Override
  public boolean isValid(RegisterForm form, ConstraintValidatorContext context){

    if(form.getPassword() == null || form.getPasswordConfirmation() == null) {
      return true;
    }

    boolean isMatch= form.getPassword().equals(form.getPasswordConfirmation());

    if(!isMatch){
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("passwordConfirmation")
          .addConstraintViolation();
    }
    return isMatch;
  }
}
