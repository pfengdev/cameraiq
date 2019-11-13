package com.app.cameraIq.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleException(EntityNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Entity not found");
        return buildResponseEntity(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleException(EntityExistsException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Entity exists");
        return buildResponseEntity(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    protected ResponseEntity<Object> handleException(NoSuchFieldException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Incorrect value for field");
        return buildResponseEntity(errorResponse, BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse,
                                                       HttpStatus httpStatus) {
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
