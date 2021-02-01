package com.morlimoore.piggybankapi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public abstract class BaseDTO {

    @Email(message="Please enter a valid Email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message="Please enter a password")
    @Size(min = 6, message = "Password cannot be less than six(6) characters")
    private String password;
}
