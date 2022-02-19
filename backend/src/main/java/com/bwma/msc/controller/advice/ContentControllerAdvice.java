package com.bwma.msc.controller.advice;


import com.bwma.msc.exception.ContentNotFoundException;
import com.bwma.msc.exception.InvalidIDException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ContentControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ContentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String contentNotFoundHandler(ContentNotFoundException error) {
        return error.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidIDException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invalidIdHandler(InvalidIDException error) {
        return error.getMessage();
    }
}
