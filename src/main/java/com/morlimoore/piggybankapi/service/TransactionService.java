package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.UserTransactionDTO;

public interface TransactionService {

    void makeTransaction(UserTransactionDTO userTransactionDTO, String username, String type);
}
