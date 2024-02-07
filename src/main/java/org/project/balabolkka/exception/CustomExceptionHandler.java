package org.project.balabolkka.exception;


import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.project.balabolkka.exception.dto.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    private static final String ARGUMENT_NOT_VALID_MESSAGE = "잘못된 입력 값 입니다";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult method(MethodArgumentNotValidException me) {

        List<FieldError> fieldErrors = me.getBindingResult().getFieldErrors();

        String message = ARGUMENT_NOT_VALID_MESSAGE;

        if (!fieldErrors.isEmpty()) {
            message = fieldErrors.get(0).getDefaultMessage();
        }

        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult notFoundExceptionHandler(NotFoundException ne) {
        return new ErrorResult(HttpStatus.NOT_EXTENDED.value(), ne.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult alreadyExceptionHandler(AlreadyExistsException ae) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), ae.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult badRequestExceptionHander(BadRequestException be) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), be.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResult validationException(ValidationException e) {
        log.error("ValidationException : ", e);
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}
