package com.trianaSalesianos.tofuApp.validation.validator;

import com.trianaSalesianos.tofuApp.validation.annotation.ValidDate;
import com.trianaSalesianos.tofuApp.validation.annotation.ValidHexColor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidHexColorValidator implements ConstraintValidator<ValidHexColor, String> {

    String patternString;

    @Override
    public void initialize(ValidHexColor constraintAnnotation) {
        patternString = constraintAnnotation.pattern();
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
