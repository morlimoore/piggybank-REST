package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.LoginUserRequestDto;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDto;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse<String>> signup(RegisterUserRequestDto registerUserRequestDto);

    ResponseEntity<ApiResponse<String>> verifyAccount(String token);

    ResponseEntity<Object> login(LoginUserRequestDto loginUserRequestDto);
}
