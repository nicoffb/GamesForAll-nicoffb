package com.salesianostriana.meal.validation.validator;

import com.salesianostriana.meal.validation.annotation.FieldsDiffer;
import com.salesianostriana.meal.validation.annotation.FieldsMatch;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsDifferValidator implements ConstraintValidator<FieldsDiffer, Object> {

    private String field;
    private String secondField;

    @Override
    public void initialize(FieldsDiffer constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.secondField = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object field1 = PropertyAccessorFactory.forBeanPropertyAccess(o).getPropertyValue(this.field);
        Object field2 = PropertyAccessorFactory.forBeanPropertyAccess(o).getPropertyValue(this.secondField);

        return !field1.equals(field2);
    }

}
