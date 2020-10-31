package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.entities.Transaction;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.repositories.TransactionRepository;
import com.morlimoore.piggybankapi.repositories.UserRepository;
import com.morlimoore.piggybankapi.service.BankingService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class BankingServiceImpl implements BankingService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public List<Transaction> getUserTransactions(Long user_id) {
        List<Transaction> response = transactionRepository.findAllTransactionsByUser(user_id);
        Collections.reverse(response);
        return response;
    }

    @Override
    public void withdraw(Transaction transaction, User user, String remarks)  {
        if (isEligibleToWithdraw(user.getId(), transaction.getAmount())) {
            transaction.setUser(user);
            transaction.setType("Withdrawal");
            transaction.setRemarks(remarks);
            transactionRepository.save(transaction);
        } else {
            throw new CustomException("Insufficient balance to withdraw", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deposit(Transaction transaction, User user, String remarks) {
        transaction.setUser(user);
        transaction.setType("Deposit");
        transaction.setRemarks(remarks);
        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(transaction);
    }

    @Override
    public boolean isEligibleToWithdraw(Long user_id, Long amount) {
        Long balance = getUserAccountBalance(user_id);
        return balance > amount;
    }

    @Override
    public String getUserAccountBalanceFormatted(Long user_id) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        return numberFormat.format(getUserAccountBalance(user_id));
    }

    @Override
    public Long getUserAccountBalance(Long user_id) {
        List<Transaction> transactions = getUserTransactions(user_id);
        Long sumOfDeposits = transactionSum(transactions, "Deposit");
        Long sumOfWithdrawals = transactionSum(transactions, "Withdrawal");
        return sumOfDeposits - sumOfWithdrawals;
    }

    private Long transactionSum(List<Transaction> transactions, String transactionType) {
        Long sum = transactions.stream()
                .filter(t -> t.getType().equals(transactionType))
                .map(t -> t.getAmount())
                .reduce((long) 0, (a, b) -> a + b);
        return sum;
    }
}