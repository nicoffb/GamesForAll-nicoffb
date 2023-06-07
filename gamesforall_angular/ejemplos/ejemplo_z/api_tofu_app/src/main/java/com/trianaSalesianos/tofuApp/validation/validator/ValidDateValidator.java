package com.trianaSalesianos.tofuApp.validation.validator;

import com.trianaSalesianos.tofuApp.validation.annotation.StrongPassword;
import com.trianaSalesianos.tofuApp.validation.annotation.UniqueUsername;
import com.trianaSalesianos.tofuApp.validation.annotation.ValidDate;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {
    String pattern;
    DateTimeFormatter formatter;
    @Override
    public void initialize(ValidDate constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        formatter =DateTimeFormatter.ofPattern(pattern);
        try {
            formatter.parse(s);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
