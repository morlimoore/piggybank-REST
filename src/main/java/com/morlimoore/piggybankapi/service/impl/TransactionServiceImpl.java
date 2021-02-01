package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.dto.TransactionDTO;
import com.morlimoore.piggybankapi.entities.Transaction;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.repositories.UserRepository;
import com.morlimoore.piggybankapi.service.BankingService;
import com.morlimoore.piggybankapi.service.TransactionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.morlimoore.piggybankapi.util.TransactionEnum.*;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private ModelMapper modelMapper;
    private BankingService bankingService;
    private UserRepository userRepository;

    @Override
    public void makeTransaction(TransactionDTO transactionDTO, User user) throws CustomException {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        System.out.println("Type: " + transaction.getType());

        if (transaction.getType().equals(WITHDRAWAL)) {
            bankingService.withdraw(transaction, user,"WITHDRAWAL by SELF");

        } else if (transaction.getType().equals(DEPOSIT)) {
            bankingService.deposit(transaction, user,"DEPOSIT By SELF");

        } else if (transaction.getType().equals(TRANSFER)) {
            Optional<User> optional = userRepository.getUserByEmail(transactionDTO.getRecipientEmail());
            User recipient = optional.orElseThrow(
                    () -> new CustomException("Provided recipient is not a customer of this bank.",
                            HttpStatus.BAD_REQUEST));
            bankingService.withdraw(transaction, user, "OUTBOUND TRANSFER to " + recipient.getEmail());
            Transaction transaction2 = new Transaction();
            transaction2.setAmount(transaction.getAmount());
            bankingService.deposit(transaction2, recipient, "INBOUND TRANSFER from " + user.getEmail());
        }

    }
}