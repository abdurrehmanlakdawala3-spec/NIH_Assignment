package com.example.studentmgmt.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CourseCodeFormatValidator implements ConstraintValidator<CourseCodeFormat, String> {

    private static final Pattern PATTERN = Pattern.compile("^[A-Z]{2,4}-\\d{3}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (!PATTERN.matcher(value).matches()) {
            return false;
        }

        return true;
    }
}
