package com.example.exception;

public class FieldNotFoundException extends RuntimeException {

    public FieldNotFoundException(String message) {
        super(message);
    }
}