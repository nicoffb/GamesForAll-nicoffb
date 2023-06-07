package com.salesianostriana.meal.validation.annotation;

import com.salesianostriana.meal.validation.validator.FieldsDifferValidator;
import com.salesianostriana.meal.validation.validator.FieldsMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = FieldsDifferValidator.class)
@Documented
public @interface FieldsDiffer {

    String message() default "Los dos campos deben ser distintos.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

    String field();
    String secondField();

}
