package com.volkswagen.quizportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotExists.class)
    public ResponseEntity<CustomErrorDetails> handleUserNotExistsException(UserNotExists exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyList.class)
    public ResponseEntity<CustomErrorDetails> handleEmptyListException(EmptyList exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TopicNotFound.class)
    public ResponseEntity<CustomErrorDetails> handleEmptyListException(TopicNotFound exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<CustomErrorDetails> handleEmptyListException(EmailAlreadyExists exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotExists.class)
    public ResponseEntity<CustomErrorDetails> handleEmptyListException(EmailNotExists exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<CustomErrorDetails> handleEmptyListException(InvalidPassword exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuestionCountZero.class)
    public ResponseEntity<CustomErrorDetails> handleEmptyListException(QuestionCountZero exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(  QuestionNotFound.class)
    public ResponseEntity<CustomErrorDetails> handleEmptyListException(  QuestionNotFound exception,WebRequest webRequest){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
