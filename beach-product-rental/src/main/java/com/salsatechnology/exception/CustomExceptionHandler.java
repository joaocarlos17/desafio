package com.salsatechnology.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.salsatechnology.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFound(NotFoundException e){
        return new ResponseEntity<>( new NotFoundException(e.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> methodArgumentNotValid(MethodArgumentNotValidException e){
        List<String> errors =  e.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, String>> invalidFormatException(InvalidFormatException e){
        return new ResponseEntity<>(getError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    private Map<String, List<String>> getErrorsMap(List<String> errors){
        Map<String,List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors",errors);
        return errorResponse;
    }

    private Map<String, String> getError(String error){
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error",error);
        return errorResponse;
    }
}
