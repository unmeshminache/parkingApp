package com.infy.parking.assignment.jpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=VehicleRegistartionNotFoundException.class)
        public ResponseEntity<String> handleException(VehicleRegistartionNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value=RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
}
