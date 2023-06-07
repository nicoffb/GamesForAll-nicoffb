package com.trianaSalesianos.tofuApp.validation.annotation;

import com.trianaSalesianos.tofuApp.validation.validator.UniqueCategoryValidator;
import com.trianaSalesianos.tofuApp.validation.validator.UniqueTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCategoryValidator.class)
@Documented
public @interface UniqueCategory {
    String message() default "The category provided already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
