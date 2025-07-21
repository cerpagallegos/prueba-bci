package com.bci.usermanagement.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${app.password.regex}")
    private String regex;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches(regex);
    }
}
