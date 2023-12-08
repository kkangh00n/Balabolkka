package org.project.balabolkka.exception;


import java.util.List;
import org.project.balabolkka.exception.dto.ErrorResult;
import org.project.balabolkka.exception.dto.ValidErrorResult;
import org.project.balabolkka.exception.exceptions.AlreadyExistsException;
import org.project.balabolkka.exception.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    private final String INTERNAL_SERVER_ERROR_MESSAGE = "서버 에러입니다.";

    @ExceptionHandler
    public ErrorResult method(MethodArgumentNotValidException me){

        BindingResult bindingResult = me.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ValidErrorResult> fieldErrorList = fieldErrors.stream()
            .map(error -> new ValidErrorResult(error.getField(),
                error.getRejectedValue().toString(), error.getDefaultMessage())
            )
            .toList();

        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), fieldErrorList);
    }

    @ExceptionHandler
    public ErrorResult notFoundExceptionHandler(NotFoundException ne){
        return new ErrorResult(HttpStatus.NOT_EXTENDED.value(), ne.getMessage());
    }

    @ExceptionHandler
    public ErrorResult alreadyExceptionHandler(AlreadyExistsException ae){
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), ae.getMessage());
    }

    @ExceptionHandler
    public ErrorResult serverError(Exception e){
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR_MESSAGE);
    }

}
