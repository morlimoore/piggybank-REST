package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.RegisterUserRequestDto;

public interface AuthService {

    void signup(RegisterUserRequestDto registerUserRequestDto);

    void verifyAccount(String token);
}
