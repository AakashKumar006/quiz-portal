package com.volkswagen.quizportal.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class CustomErrorDetails {

    private Date timestamp;
    private String message;
    private String errorDetails;
    private HttpStatus status;

    public CustomErrorDetails(Date timestamp, String message, String errorDetails, HttpStatus status) {
        this.timestamp = timestamp;
        this.message = message;
        this.errorDetails = errorDetails;
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
