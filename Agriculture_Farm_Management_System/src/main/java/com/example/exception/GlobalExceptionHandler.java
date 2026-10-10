package com.example.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.constants.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FarmerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFarmerNotFoundException(
            FarmerNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(FarmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFarmNotFoundException(
            FarmNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(FieldNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFieldNotFoundException(
            FieldNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(CropNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCropNotFoundException(
            CropNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleActivityNotFoundException(
            ActivityNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleExpenseNotFoundException(
            ExpenseNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(HarvestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleHarvestNotFoundException(
            HarvestNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(
            MethodArgumentNotValidException ex) {

        return Constants.VALIDATION_FAILED;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex) {

        return Constants.SOMETHING_WENT_WRONG;
    }
    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFileStorageException(
            FileStorageException ex) {

        return ex.getMessage();
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Returns HTTP 400 instead of 500
    }
}