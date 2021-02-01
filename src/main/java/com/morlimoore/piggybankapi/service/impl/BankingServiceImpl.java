package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.entities.Transaction;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.repositories.TransactionRepository;
import com.morlimoore.piggybankapi.repositories.UserRepository;
import com.morlimoore.piggybankapi.service.BankingService;
import com.morlimoore.piggybankapi.util.TransactionEnum;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.morlimoore.piggybankapi.util.TransactionEnum.DEPOSIT;
import static com.morlimoore.piggybankapi.util.TransactionEnum.WITHDRAWAL;

@Service
@AllArgsConstructor
public class BankingServiceImpl implements BankingService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public List<Transaction> getUserTransactions(User user) {
        List<Transaction> response = transactionRepository.findAllTransactionsByUser(user);
        Collections.reverse(response);
        return response;
    }

    @Override
    public void withdraw(Transaction transaction, User user, String remarks)  {
        if (isEligibleToWithdraw(user, transaction.getAmount())) {
            transaction.setUser(user);
            transaction.setRemarks(remarks);
            transactionRepository.save(transaction);
        } else {
            throw new CustomException("Insufficient balance to withdraw", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public void deposit(Transaction transaction, User recipient, String remarks) {
        transaction.setUser(recipient);
        transaction.setRemarks(remarks);
//        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(transaction);
    }

    @Override
    public boolean isEligibleToWithdraw(User user, Long amount) {
        Long balance = getUserAccountBalance(user);
        return balance > amount;
    }

    @Override
    public String getUserAccountBalanceFormatted(User user) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        return numberFormat.format(getUserAccountBalance(user));
    }

    @Override
    public Long getUserAccountBalance(User user) {
        List<Transaction> transactions = getUserTransactions(user);
        Long sumOfDeposits = transactionSum(transactions, DEPOSIT);
        Long sumOfWithdrawals = transactionSum(transactions, WITHDRAWAL);
        return sumOfDeposits - sumOfWithdrawals;
    }

    private Long transactionSum(List<Transaction> transactions, TransactionEnum transactionType) {
        Long sum = transactions.stream()
                .filter(t -> t.getType().equals(transactionType))
                .map(t -> t.getAmount())
                .reduce((long) 0, (a, b) -> a + b);
        return sum;
    }
}