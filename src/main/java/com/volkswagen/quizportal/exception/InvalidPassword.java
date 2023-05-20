package com.volkswagen.quizportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidPassword extends Exception {
    public InvalidPassword(String message) {
        super(message);
    }
}
