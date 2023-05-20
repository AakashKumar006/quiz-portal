package com.volkswagen.quizportal.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNoValidator implements ConstraintValidator<PhoneNoValid, String> {
    public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
        // regex
        // (0|91) phone number must starts with 0 or 91
        // [7-9]{1} then next 1 digit should be from 7 to 9
        // [0-9]{9} then next 9 digit should be from 0 to 9
        // {10} total of then should be 10
        // ex - 0 8750112894, 91 8750112894

        Pattern pattern = Pattern.compile("(0|91)?[[7-9]{1}[0-9]{9}]{10}");
        Matcher matcher = pattern.matcher(mobileNumber);
        return (matcher.find() && matcher.group().equals(mobileNumber));
    }
}
