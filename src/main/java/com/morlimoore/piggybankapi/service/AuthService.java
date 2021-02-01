package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.LoginUserRequestDTO;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDTO;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse<String>> signup(RegisterUserRequestDTO registerUserRequestDto);

    ResponseEntity<?> login(LoginUserRequestDTO loginUserRequestDto);

    ResponseEntity<ApiResponse<String>> verifyAccount(String token);

}
