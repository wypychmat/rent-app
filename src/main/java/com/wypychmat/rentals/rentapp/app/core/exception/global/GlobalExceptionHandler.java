package com.wypychmat.rentals.rentapp.app.core.exception.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.DATA_PATTERN;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler() {
        objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    String getErrorBody(HttpStatus status,String path) throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                new GlobalException(status,
                        new SimpleDateFormat(DATA_PATTERN).format(new Date()),
                        status.getReasonPhrase(), path));
    }
}
