package com.morlimoore.piggybankapi.controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.morlimoore.piggybankapi.dto.LoginUserRequestDTO;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDTO;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.morlimoore.piggybankapi.util.CreateResponse.*;
import static com.morlimoore.piggybankapi.util.Validator.validateDateOfBirth;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody RegisterUserRequestDTO registerUserRequestDto, BindingResult result) throws UnirestException {
        if (result.hasErrors())
            return bindingResultError(result);
        else if (!validateDateOfBirth(registerUserRequestDto.getDateOfBirth())) {
            ApiResponse<String> response = new ApiResponse<>(BAD_REQUEST);
            response.setError("User must be equal to or above 18 years of age");
            response.setMessage("Invalid date of birth");
            return createResponse(response);
        } else if (authService.signup(registerUserRequestDto)) {
            ApiResponse<String> response = new ApiResponse<>(OK);
            response.setData("User Registration Successful");
            response.setMessage("Success");
            return createResponse(response);
        }
        ApiResponse<String> response = new ApiResponse<>(INTERNAL_SERVER_ERROR);
        response.setError(response.getError());
        response.setMessage("Failure");
        return createResponse(response);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<ApiResponse<String>> verifyAccount(@PathVariable String token) {
        return authService.verifyAccount(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginUserRequestDTO loginUserRequestDto) {
        return authService.login(loginUserRequestDto);
    }
}
