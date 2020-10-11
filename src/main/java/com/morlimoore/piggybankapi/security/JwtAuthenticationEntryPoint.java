package com.morlimoore.piggybankapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        HttpStatus status = HttpStatus.FORBIDDEN;
        String error = "FORBIDDEN";
        String message = "";
        if (e.getMessage().contains("JWT expired at")){
            status = HttpStatus.UNAUTHORIZED;
            error = "Token has expired";
            message = e.getMessage();
        }
        ApiResponse<?> res = new ApiResponse<>(status);
        res.setError(error);
        res.setMessage(message);
        //set the response headers
        httpServletResponse.setStatus(status.value());
        httpServletResponse.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(mapper.writeValueAsString(res));
        out.flush();
    }
}