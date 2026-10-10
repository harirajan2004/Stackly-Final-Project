package com.example.exception;

public class HarvestNotFoundException extends RuntimeException {

    public HarvestNotFoundException(String message) {
        super(message);
    }
}