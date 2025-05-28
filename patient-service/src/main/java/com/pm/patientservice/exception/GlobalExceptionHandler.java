package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String,String>> handleValidationException(MethodArgumentNotValidException ex){

    HashMap<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error->{errors.put(error.getField(),error.getDefaultMessage());});

    return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<HashMap<String,String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){

       log.warn("Email already exists {}", ex.getMessage());
        HashMap<String,String> errors = new HashMap<>();
        errors.put("message","Email id is already in use");
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<HashMap<String,String>> handlePatientNotFound(PatientNotFoundException ex) {
        log.warn("Patient not found {}", ex.getMessage());
        HashMap<String,String> errors = new HashMap<>();
        errors.put("message","Patient not found");
        return ResponseEntity.badRequest().body(errors);

    }

}
