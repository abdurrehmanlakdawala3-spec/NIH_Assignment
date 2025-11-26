package com.example.studentmgmt.validation;

import com.example.studentmgmt.repository.CourseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueCourseCodeValidator implements ConstraintValidator<UniqueCourseCode, String> {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return !courseRepository.existsByCourseCodeIgnoreCase(value);
    }
}
