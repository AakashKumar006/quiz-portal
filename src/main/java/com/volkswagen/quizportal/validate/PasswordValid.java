package com.volkswagen.quizportal.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {

    String message() default "Password must contain 6 to 15 characters, one lowercase letter, one uppercase letter, one numeric digit, and one special character}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
