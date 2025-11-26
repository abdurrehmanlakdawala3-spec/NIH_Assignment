package com.example.studentmgmt.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CourseCodeFormatValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCodeFormat {
    String message() default "Invalid course code (expected format: ABC-123)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
