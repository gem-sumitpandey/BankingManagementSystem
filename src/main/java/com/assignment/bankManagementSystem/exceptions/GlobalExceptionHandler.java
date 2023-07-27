package com.assignment.bankManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundExceptionHandler(ResourceNotFoundException e){

     return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> insufficientBalanceExceptionHandler(InsufficientBalanceException e){

        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<String> invalidArgumentExceptionHandler(InvalidArgumentException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintException.class)
    public ResponseEntity<String> constraintExceptionHandler(ConstraintException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException exception){
        Map<String,String > errorMap= new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }

}
