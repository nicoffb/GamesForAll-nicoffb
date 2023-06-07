package com.trianaSalesianos.tofuApp.validation.annotation;

import com.trianaSalesianos.tofuApp.validation.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {

    String message() default "The username provided already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}