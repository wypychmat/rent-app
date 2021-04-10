package com.wypychmat.rentals.rentapp.app.core.exception.register;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wypychmat.rentals.rentapp.app.core.util.Constant;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.*;

abstract class BasicExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectWriter objectWriter;

    abstract Class<? extends RuntimeException> setView();

    BasicExceptionHandler() {
        objectWriter = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writerWithView(setView());
    }

    String getBody(BasicErrorResponse basicErrorResponse) throws JsonProcessingException {
        basicErrorResponse.setIssuedAt(new SimpleDateFormat(DATA_PATTERN).format(new Date()));
        return objectWriter.writeValueAsString(basicErrorResponse);
    }
}
