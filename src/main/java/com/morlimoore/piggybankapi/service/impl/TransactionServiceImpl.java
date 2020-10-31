package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.dto.UserTransactionDTO;
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

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private ModelMapper modelMapper;
    private BankingService bankingService;
    private UserRepository userRepository;

    @Override
    public void makeTransaction(UserTransactionDTO userTransactionDTO, String username, String type) throws CustomException {
        if (userTransactionDTO != null && username != null) {
            Transaction transaction = modelMapper.map(userTransactionDTO, Transaction.class);
            Optional<User> userOptional = userRepository.getUserByEmail(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (type.equals("Withdrawal")) {
                    bankingService.withdraw(transaction, user, "By SELF");

                } else if (type.equals("Deposit")) {
                    bankingService.deposit(transaction, user, "By SELF");

                } else if (type.equals("Transfer")) {
                    Optional<User> optional = userRepository.getUserByEmail(userTransactionDTO.getRecipientEmail());
                    User recipient = optional.orElseThrow(() -> new CustomException("Entered email is not a customer", HttpStatus.BAD_REQUEST));
                    bankingService.withdraw(transaction, user, "TRF-OUT to " + recipient.getEmail());
                    Transaction transaction2 = new Transaction();
                    transaction2.setAmount(transaction.getAmount());
                    bankingService.deposit(transaction2, recipient, "TRF-IN from " + username);
                }
            } else {
                throw new CustomException("Transaction failed", HttpStatus.BAD_REQUEST);
            }
        }
    }
}