package com.example.mybinance.error;

public class ApiError extends Exception {

    String message;
    public ApiError(String message) {
        this.message = message;
    }

}
