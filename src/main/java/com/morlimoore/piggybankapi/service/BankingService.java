package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.entities.Transaction;
import com.morlimoore.piggybankapi.entities.User;

import java.util.List;

public interface BankingService {

    List<Transaction> getUserTransactions(User user);

    Long getUserAccountBalance(User user);

    String getUserAccountBalanceFormatted(User user);

    void withdraw(Transaction transaction, User user, String remarks);

    void deposit(Transaction transaction, User user, String remarks);

    boolean isEligibleToWithdraw(User user, Long amount);

}