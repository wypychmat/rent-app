package com.wypychmat.rentals.rentapp.app.core.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wypychmat.rentals.rentapp.app.core.exception.dto.BasicErrorResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BasicExceptionHandler extends ResponseEntityExceptionHandler {

    private final SimpleDateFormat simpleDateFormat;
    private final ObjectWriter objectWriter;

    protected abstract Class<? extends RuntimeException> setView();

    public BasicExceptionHandler() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        objectWriter = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writerWithView(setView());
    }

    String getBody(BasicErrorResponse basicErrorResponse) throws JsonProcessingException {
        basicErrorResponse.setIssuedAt(simpleDateFormat.format(new Date()));
        return objectWriter.writeValueAsString(basicErrorResponse);
    }
}
