package com.morlimoore.piggybankapi.controllers;

import com.morlimoore.piggybankapi.dto.LoginUserRequestDto;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDto;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto, BindingResult result) {
        return authService.signup(registerUserRequestDto, result);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<ApiResponse<String>> verifyAccount(@PathVariable String token) {
        return authService.verifyAccount(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginUserRequestDto loginUserRequestDto) {
        return authService.login(loginUserRequestDto);
    }
}
