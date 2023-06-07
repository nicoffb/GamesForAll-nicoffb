package com.trianaSalesianos.tofuApp.validation.annotation;

import com.trianaSalesianos.tofuApp.validation.validator.ValidDateValidator;
import com.trianaSalesianos.tofuApp.validation.validator.ValidHexColorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidHexColorValidator.class)
@Documented
public @interface ValidHexColor {
    String message() default "The color provided is not a Hexadecimal color (#FFFFFF)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
}
