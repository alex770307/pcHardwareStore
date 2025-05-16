package org.pchardwarestore.controller;

import jakarta.validation.ConstraintViolationException;
import org.pchardwarestore.service.exception.AlreadyExistException;
import org.pchardwarestore.service.exception.NotFoundException;
import org.pchardwarestore.service.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(AlreadyExistException.class)
//    public ResponseEntity<String> handlerAlreadyExistException(AlreadyExistException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
@ExceptionHandler(AlreadyExistException.class)
public ResponseEntity<Map<String, String>> handlerAlreadyExistException(AlreadyExistException e){
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error",e.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
}

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(NotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handlerValidationException(ValidationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handlerNullPointerException(NullPointerException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handlerConstraintViolationException(ConstraintViolationException e){

        StringBuilder responseMessage = new StringBuilder();

        e.getConstraintViolations().forEach(
                constraintViolation -> {
                    String currentErrorMessage = constraintViolation.getMessage();
                    responseMessage.append(currentErrorMessage);
                    responseMessage.append("\n");
                }
        );

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){

        StringBuilder responseMessage = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach(
                objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String currentErrorMessage = objectError.getDefaultMessage();
                    responseMessage.append(fieldName + " : " + currentErrorMessage);
                    responseMessage.append("\n");
                }
        );

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
