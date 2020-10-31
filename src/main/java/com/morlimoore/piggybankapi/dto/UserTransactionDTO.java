package com.morlimoore.piggybankapi.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserTransactionDTO {

    @NotNull(message = "Transaction type cannot be null")
    private String type;

    @Min(value = 1000L , message = "Transaction amount must be greater than one thousand")
    private Long amount;

    @Past
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull(message = "UserId cannot be null")
    private Long userId;

    @Email
    private String recipientEmail;

}
