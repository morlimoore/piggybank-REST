package com.morlimoore.piggybankapi.controllers;

import com.morlimoore.piggybankapi.dto.TransactionDTO;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.service.TransactionService;
import com.morlimoore.piggybankapi.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.morlimoore.piggybankapi.util.CreateResponse.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    @Autowired
    private final AuthUtil authUtil;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private TransactionService transactionService;

    //TODO: Troubleshoot that the current deposit transaction does wipe off the initial transaction
    @PostMapping("/execute")
    public ResponseEntity<ApiResponse<String>> deposit(@Valid @RequestBody TransactionDTO transactionDTO,
                                                       BindingResult result) {
        if (result.hasErrors())
            return errorResponse(result.getFieldError().getDefaultMessage(), UNPROCESSABLE_ENTITY);

        User user = authUtil.getAuthenticatedUser();
        transactionService.makeTransaction(transactionDTO, user);
        return successResponse(String.format("Funds %s was successful.",
                transactionDTO.getType().toLowerCase()), CREATED);
    }




}
