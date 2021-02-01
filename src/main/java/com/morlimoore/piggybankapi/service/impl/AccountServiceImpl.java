package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.dto.AccountInfoDTO;
import com.morlimoore.piggybankapi.dto.TransactionInfoDTO;
import com.morlimoore.piggybankapi.entities.Transaction;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.service.AccountService;
import com.morlimoore.piggybankapi.service.BankingService;
import com.morlimoore.piggybankapi.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.morlimoore.piggybankapi.util.CreateResponse.createResponse;
import static org.springframework.http.HttpStatus.OK;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BankingService bankingService;

    @Override
    public ResponseEntity<ApiResponse<AccountInfoDTO>> getAccountInfo() {
        User user = authUtil.getAuthenticatedUser();
        AccountInfoDTO accountInfoDTO = modelMapper.map(user, AccountInfoDTO.class);
        List<Transaction> transactions = bankingService.getUserTransactions(user);
        List<TransactionInfoDTO> txList = new ArrayList<>();
        transactions.forEach(t -> {
            TransactionInfoDTO tx = modelMapper.map(t, TransactionInfoDTO.class);
            tx.setDate(t.getCreatedAt());
            txList.add(tx);
        });
        accountInfoDTO.setTransactions(txList);
        Long balance = bankingService.getUserAccountBalance(user);
        accountInfoDTO.setAvailableBalance(balance);
        ApiResponse<AccountInfoDTO> response = new ApiResponse<>();
        response.setStatus(OK);
        response.setMessage("SUCCESS");
        response.setResult(accountInfoDTO);
        return createResponse(response);
    }
}