package com.rapidshine.carwash.user_service.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorResponse {
    public String message;
    public LocalDateTime localDate;
    public int httpStatus;

    public ErrorResponse(String message, LocalDateTime localDate, int httpStatus) {
        this.message = message;
        this.localDate = localDate;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
