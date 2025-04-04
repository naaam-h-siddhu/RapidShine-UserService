package com.rapidshine.carwash.user_service.exceptions;

public class ErrorResponse {
    public String message;

    public String getMessage() {
        return message;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
