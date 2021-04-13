package com.wypychmat.rentals.rentapp.app.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wypychmat.rentals.rentapp.app.core.exception.global.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wypychmat.rentals.rentapp.app.core.util.Constant.DATA_PATTERN;
import static com.wypychmat.rentals.rentapp.app.core.util.Constant.JSON_CONTENT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;


class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        String path = httpServletRequest.getRequestURI();
        GlobalException body = new GlobalException(HttpStatus.FORBIDDEN,
                new SimpleDateFormat(DATA_PATTERN).format(new Date()),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                path);
        httpServletResponse.addHeader(CONTENT_TYPE, JSON_CONTENT);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), body);
    }
}
