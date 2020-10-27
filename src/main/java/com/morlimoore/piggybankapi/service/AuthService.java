package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.LoginUserRequestDto;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDto;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface AuthService {

    Boolean signup(RegisterUserRequestDto registerUserRequestDto);

    ResponseEntity<ApiResponse<String>> verifyAccount(String token);

    ResponseEntity<Object> login(LoginUserRequestDto loginUserRequestDto);
}
