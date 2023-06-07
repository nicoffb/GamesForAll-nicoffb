package com.salesianostriana.dam.flalleryapi.validation.annotation;


import com.salesianostriana.dam.flalleryapi.validation.validator.StrongPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface StrongPassword {

    String message() default "El nombre de usuario ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 8;
    int max() default Integer.MAX_VALUE;

    boolean hasUpper() default true;
    boolean hasLower() default true;

    boolean hasAlpha() default true;
    boolean hasNumber() default true;

    boolean hasSpecial() default true;


}
