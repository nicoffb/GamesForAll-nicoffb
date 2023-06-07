package com.trianaSalesianos.tofuApp.validation.validator;

import com.trianaSalesianos.tofuApp.service.TypeService;
import com.trianaSalesianos.tofuApp.service.UserService;
import com.trianaSalesianos.tofuApp.validation.annotation.UniqueType;
import com.trianaSalesianos.tofuApp.validation.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueTypeValidator implements ConstraintValidator<UniqueType, String> {

    @Autowired
    private TypeService typeService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && !typeService.typeExists(s);
    }
}
