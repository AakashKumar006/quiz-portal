package com.volkswagen.quizportal.validate;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneNoValidator.class)
public @interface PhoneNoValid {

    // error message
    String message() default "Invalid phone number";

    // represent group of constraints
    Class<?>[] groups() default {};

    // additional information about the annotation
    Class<? extends Payload>[] payload() default {};
}
