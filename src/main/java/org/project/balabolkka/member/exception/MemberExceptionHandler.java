package org.project.balabolkka.member.exception;


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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult notFoundExceptionHandler(NotFoundException ne){
        return new ErrorResult(HttpStatus.NOT_EXTENDED.value(), ne.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult alreadyExceptionHandler(AlreadyExistsException ae){
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), ae.getMessage());
    }

}
