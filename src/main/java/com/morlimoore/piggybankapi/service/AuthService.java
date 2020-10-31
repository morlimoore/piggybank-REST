package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.LoginUserRequestDTO;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDTO;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    Boolean signup(RegisterUserRequestDTO registerUserRequestDto);

    ResponseEntity<ApiResponse<String>> verifyAccount(String token);

    ResponseEntity<Object> login(LoginUserRequestDTO loginUserRequestDto);
}
