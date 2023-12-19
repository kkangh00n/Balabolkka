package org.project.balabolkka.login.exception;

import org.project.balabolkka.exception.dto.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult badCredentialsExceptionHandler(BadCredentialsException be){
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), be.getMessage());
    }

}
