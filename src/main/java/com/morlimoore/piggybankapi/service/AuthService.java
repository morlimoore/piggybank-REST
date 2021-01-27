package com.morlimoore.piggybankapi.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.morlimoore.piggybankapi.dto.LoginUserRequestDTO;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDTO;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.payload.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse<String>> signup(RegisterUserRequestDTO registerUserRequestDto) throws UnirestException;

    ResponseEntity<?> login(LoginUserRequestDTO loginUserRequestDto);

    ResponseEntity<ApiResponse<String>> verifyAccount(String token);

}
