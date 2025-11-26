package com.example.studentmgmt.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueCourseCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCourseCode {
    String message() default "Course Code already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
