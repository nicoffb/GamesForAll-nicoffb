package com.salesianostriana.meal.validation.annotation;

import com.salesianostriana.meal.validation.validator.FieldsMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = FieldsMatchValidator.class)
@Documented
public @interface FieldsMatch {

    String message() default "Los dos campos deben ser iguales.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

    String field();
    String fieldMatch();

}
