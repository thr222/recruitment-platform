package com.company.recruit.common.web.handler;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.common.core.exception.BusinessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException exception) {
        return Result.failure(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(error -> error.getField() + " " + error.getDefaultMessage())
            .orElse("请求参数不合法");
        return Result.failure("VALIDATION_ERROR", message);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleUnknown(Exception exception) {
        return Result.failure("INTERNAL_ERROR", exception.getMessage());
    }
}
