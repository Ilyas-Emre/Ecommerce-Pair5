package org.turkcell.ecommercepair5.util.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.turkcell.ecommercepair5.util.exception.result.BusinessExceptionResult;
import org.turkcell.ecommercepair5.util.exception.result.ExceptionResult;
import org.turkcell.ecommercepair5.util.exception.result.ExpiredJwtExceptionResult;
import org.turkcell.ecommercepair5.util.exception.result.ValidationExceptionResult;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    /*@ExceptionHandler({Exception.class})
    *@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResult handleException(Exception e) {
        return new ExceptionResult("InternalServerError");
    }*/

    // İş kuralı ihlalleri için özel handler
    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BusinessExceptionResult handleRuntimeException(BusinessException e) {
        return new BusinessExceptionResult(e.getMessage());
    }

    // Validation (Method Argument Not Valid) hataları için handler
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ValidationExceptionResult(e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map((error) -> error.getDefaultMessage())
                .toList());
    }

    //TODO: ExpiredJWTException - 403 Forbidden
    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExpiredJwtExceptionResult handleExpiredJwtException(ExpiredJwtException e){
        return new ExpiredJwtExceptionResult(e.getMessage());
    }

    // Genel RuntimeException handler
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResult handleRuntimeException(RuntimeException e){
        return new ExceptionResult("Runtime error occured " + e.getMessage());
    }

    // Beklenmeyen tüm Exception'lar için genel handler
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResult handleGeneralException(Exception e){
        return new ExceptionResult("Unexpected error occured: " + e.getMessage());
    }


    // MethodArgumentEx.
}

