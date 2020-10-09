package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.service.SignupTokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignupTokenServiceImpl implements SignupTokenService {

    @Override
    public String getToken() {
        return UUID.randomUUID().toString();
    }
}
