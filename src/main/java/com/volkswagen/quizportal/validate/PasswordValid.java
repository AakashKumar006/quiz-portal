package com.volkswagen.quizportal.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {
    // error message
    String message() default "Password must contain 6 to 15 characters, one lowercase letter, one uppercase letter, one numeric digit, and one special character}";

    // represent group of constraints
    Class<?>[] groups() default {};

    // additional information about the annotation
    Class<? extends Payload>[] payload() default {};
}
