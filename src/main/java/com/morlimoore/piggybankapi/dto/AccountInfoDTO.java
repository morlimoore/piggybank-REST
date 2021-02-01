package com.morlimoore.piggybankapi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AccountInfoDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Long availableBalance;
    private List<TransactionInfoDTO> transactions;
}