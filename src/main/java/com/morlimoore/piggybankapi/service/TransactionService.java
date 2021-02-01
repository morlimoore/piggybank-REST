package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.dto.TransactionDTO;
import com.morlimoore.piggybankapi.entities.User;

public interface TransactionService {

    void makeTransaction(TransactionDTO transactionDTO, User user);
}
