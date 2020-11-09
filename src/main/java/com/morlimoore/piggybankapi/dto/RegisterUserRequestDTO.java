package com.morlimoore.piggybankapi.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDTO extends BaseDTO {

    @NotBlank(message="First name cannot be blank")
    private String firstName;

    @NotBlank(message="Last name cannot be blank")
    private String lastName;

    @Pattern(regexp="(^$|[0-9]{11})")
    @NotBlank(message="Please provide a valid phone number")
    private String phoneNumber;


    private LocalDate dateOfBirth;

    @CreationTimestamp
    private Timestamp createdAt;
}
