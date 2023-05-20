package com.volkswagen.quizportal.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {


    public boolean isValid(String password, ConstraintValidatorContext context) {
       String regex = "^(?=.*[0-9])" // represents a digit must occur at least once.
               + "(?=.*[a-z])" // represents a lower case alphabet must occur at least once.
               + "(?=.*[A-Z])" //represents an upper case alphabet that must occur at least once.
               + "(?=.*[@#$%^&+=])" //  represents a special character that must occur at least once.
               + "(?=\\S+$)" // white spaces don’t allowed in the entire string.
               + ".{6,15}$"; // white spaces don’t allowed in the entire string.
       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(password);
       return (matcher.find() && matcher.group().equals(password));
    }
}
