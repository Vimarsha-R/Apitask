package com.example.crudApp.advice;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Exceptionhandler {
    public static class InvalidAuthorException extends Throwable {
        public InvalidAuthorException(String message) {
            super(message);
        }
    }
    public static class InvalidtitleException extends Throwable {
        public InvalidtitleException(String s) {
            super(s);
        }
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(@NotNull MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAuthorException.class)
    public String handleInvalidAuthorException(@NotNull InvalidAuthorException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidtitleException.class)
    public String handleInvalidTitleException(@NotNull InvalidtitleException ex) {
        return ex.getMessage();
    }

}
