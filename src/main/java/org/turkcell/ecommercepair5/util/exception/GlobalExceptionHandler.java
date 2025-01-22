package org.turkcell.ecommercepair5.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.turkcell.ecommercepair5.util.exception.result.BusinessExceptionResult;
import org.turkcell.ecommercepair5.util.exception.result.ValidationExceptionResult;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
  /*@ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResult handleException(Exception e) {
    // e
    return new ExceptionResult("InternalServerError");
  }*/

    // İş kuralı
    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BusinessExceptionResult handleRuntimeException(BusinessException e) {
        return new BusinessExceptionResult(e.getMessage());
    }

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
    // MethodArgumentEx.
}

