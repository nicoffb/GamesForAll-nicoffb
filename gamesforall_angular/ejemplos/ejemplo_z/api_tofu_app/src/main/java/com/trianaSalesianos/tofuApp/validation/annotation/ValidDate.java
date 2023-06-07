package com.trianaSalesianos.tofuApp.validation.annotation;

import com.trianaSalesianos.tofuApp.validation.validator.StrongPasswordValidator;
import com.trianaSalesianos.tofuApp.validation.validator.ValidDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
@Documented
public @interface ValidDate {

    String message() default "The date provided is not a valid date (dd/MM/yy)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "dd/MM/yyyy";
}
