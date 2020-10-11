package com.morlimoore.piggybankapi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public abstract class BaseDto {

    @Email(message="Please enter a valid Email address")
    private String email;

    @NotBlank(message="Please enter a password")
    private String password;
}
