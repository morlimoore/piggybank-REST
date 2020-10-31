package com.morlimoore.piggybankapi.controllers;

import com.morlimoore.piggybankapi.dto.UserTransactionDTO;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.service.TransactionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.morlimoore.piggybankapi.util.CreateResponse.bindingResultError;
import static com.morlimoore.piggybankapi.util.CreateResponse.createResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private TransactionService transactionService;

    //TODO: Troubleshoot that the current deposit transaction does not wipe off the initial transaction
    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<String>> deposit(@Valid @RequestBody UserTransactionDTO userTransactionDTO, BindingResult result) {
        ApiResponse<String> response = new ApiResponse<>();
        if (result.hasErrors()) {
            logger.error(result.getFieldError().getDefaultMessage());
            return bindingResultError(result);
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if (username != null) {
            transactionService.makeTransaction(userTransactionDTO, username, "Deposit");
            response = new ApiResponse<>(OK);
            response.setData("Account deposit was successful");
            response.setMessage("Success");
        }
        return createResponse(response);
    }




}
