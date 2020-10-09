package com.morlimoore.piggybankapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDto {

    @NotBlank(message="First name cannot be blank")
    private String first_name;

    @NotBlank(message="Last name cannot be blank")
    private String last_name;

    @Email(message="Please enter a valid Email address")
    private String email;

    @NotBlank(message="Please a password")
    private String password;

    @Pattern(regexp="(^$|[0-9]{10})")
    @NotBlank(message="Please provide a valid phone number")
    private String phone_number;

    @Past
    @NotBlank(message="Please enter a date of birth")
    private String date_of_birth;

    @CreationTimestamp
    private Timestamp created_at;
}
