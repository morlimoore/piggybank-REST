package com.morlimoore.piggybankapi.exceptions;

import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.morlimoore.piggybankapi.util.CreateResponse.createResponse;
import static com.morlimoore.piggybankapi.util.CreateResponse.errorResponse;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
        return errorResponse(e.getLocalizedMessage(), e.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException e) {
        return errorResponse(e.getMessage(), UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(ConstraintViolationException e) {
        return errorResponse(e.getLocalizedMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return errorResponse(e.getLocalizedMessage(), INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        ApiResponse response = new ApiResponse<>(BAD_REQUEST);
        response.setMessage("ERROR");
        response.setResult(e.getMostSpecificCause().getLocalizedMessage());
        return createResponse(response);
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return errorResponse("Username does not exist", UNPROCESSABLE_ENTITY);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        ApiResponse response = new ApiResponse<>(BAD_REQUEST);
        response.setMessage("ERROR");
        response.setResult(e.getBindingResult().getAllErrors());
        return createResponse(response);
    }

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(Exception ex) {
        return errorResponse("Username or password is invalid. Check and try again",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ DisabledException.class })
    public ResponseEntity<ApiResponse<String>> handleDisabledException(Exception ex) {
        return errorResponse("User account is disabled. Please activate your account, and try again",
                HttpStatus.BAD_REQUEST);
    }

}
