package com.trianaSalesianos.tofuApp.validation.validator;

import com.trianaSalesianos.tofuApp.service.CategoryService;
import com.trianaSalesianos.tofuApp.service.TypeService;
import com.trianaSalesianos.tofuApp.validation.annotation.UniqueCategory;
import com.trianaSalesianos.tofuApp.validation.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && !categoryService.categoryExists(s);
    }
}
