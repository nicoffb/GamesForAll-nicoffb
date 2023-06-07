package com.trianaSalesianos.tofuApp.validation.annotation;

import com.trianaSalesianos.tofuApp.validation.validator.UniqueTypeValidator;
import com.trianaSalesianos.tofuApp.validation.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTypeValidator.class)
@Documented
public @interface UniqueType {
    String message() default "The type provided already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
