package com.morlimoore.piggybankapi.controllers;

import com.morlimoore.piggybankapi.dto.AccountInfoDTO;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<AccountInfoDTO>> getAccountInfo() {
        return accountService.getAccountInfo();
    }
}