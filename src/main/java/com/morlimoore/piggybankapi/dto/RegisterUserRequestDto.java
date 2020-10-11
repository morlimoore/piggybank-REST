package com.morlimoore.piggybankapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDto extends BaseDto {

    @NotBlank(message="First name cannot be blank")
    private String firstName;

    @NotBlank(message="Last name cannot be blank")
    private String lastName;

    @Pattern(regexp="(^$|[0-9]{10})")
    @NotBlank(message="Please provide a valid phone number")
    private String phoneNumber;

    @Past
    @NotBlank(message="Please enter a date of birth")
    private String dateOfBirth;

    @CreationTimestamp
    private Timestamp createdAt;
}
