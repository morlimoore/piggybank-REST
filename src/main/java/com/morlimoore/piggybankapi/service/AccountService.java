package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.AccountInfoDTO;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<ApiResponse<AccountInfoDTO>> getAccountInfo();
}