package com.wypychmat.rentals.rentapp.app.core.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wypychmat.rentals.rentapp.app.core.exception.InvalidRegisterRequestException;
import com.wypychmat.rentals.rentapp.app.core.exception.dto.BasicErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ControllerAdvice
public class InvalidRegisterExceptionHandler extends ResponseEntityExceptionHandler {
    private final SimpleDateFormat simpleDateFormat;

    public InvalidRegisterExceptionHandler() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    @ExceptionHandler(value
            = { InvalidRegisterRequestException.class})
    protected ResponseEntity<Object> invalidRequestHandler(InvalidRegisterRequestException ex,
                                                           WebRequest request) throws JsonProcessingException {
        String body = new ObjectMapper()
                .writeValueAsString(new BasicErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                simpleDateFormat.format(new Date()),
                                ex.getErrors()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE,"application/json");
        return handleExceptionInternal(ex,body,httpHeaders,HttpStatus.CONFLICT,request);
    }
}
