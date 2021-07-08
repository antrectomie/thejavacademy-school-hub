package com.thejavacademy.userservice.controller;

import com.thejavacademy.userservice.exception.UserServiceException;
import com.thejavacademy.userservice.model.dto.ApiError;
import com.thejavacademy.userservice.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<ApiError> handleUserServiceException(UserServiceException ex) {
        final UserServiceException.ExceptionType type = ex.getType();
        ResponseEntity<ApiError> errorResponseEntity = null;
        switch (type) {
            case USER_NOT_FOUND:
                errorResponseEntity = new ResponseEntity<>(new ApiError(ex.getType().getCode(), ex.getType().getMessage()), HttpStatus.NOT_FOUND);
                break;
            case SERVER_ERROR:
                errorResponseEntity = new ResponseEntity<>(new ApiError(ex.getType().getCode(), ex.getType().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
                break;
            case EMPTY_USER_ID:
                errorResponseEntity = new ResponseEntity<>(new ApiError(ex.getType().getCode(), ex.getType().getMessage()), HttpStatus.BAD_REQUEST);
                break;
            default:
                errorResponseEntity = new ResponseEntity<>(new ApiError(500, "Service Temporary Unavailble"), HttpStatus.BAD_REQUEST);
        }
        return errorResponseEntity;
    }
}
