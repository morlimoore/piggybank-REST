package com.morlimoore.piggybankapi.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
public class TransactionDTO {

    @NotBlank(message = "Transaction type cannot be blank")
    private String type;

    @Min(value = 1000L , message = "Transaction amount must be greater than one thousand")
    private Long amount;

    @Email
    private String recipientEmail;
}
