package com.morlimoore.piggybankapi.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterUserRequestDtoTest {

    Timestamp date;
    LocalDate dob;

    @Test
    @DisplayName("register user request - 🌱")
    public void registerUserRequest() {
        RegisterUserRequestDTO registerUserRequestDto = new RegisterUserRequestDTO();
        registerUserRequestDto.setCreatedAt(date);
        registerUserRequestDto.setDateOfBirth(dob);
        registerUserRequestDto.setFirstName("John");
        registerUserRequestDto.setLastName("Doe");
        registerUserRequestDto.setPhoneNumber("0000");
        registerUserRequestDto.setEmail("example@app.com");
        registerUserRequestDto.setPassword("password");

        assertEquals("John", registerUserRequestDto.getFirstName());
        assertEquals("Doe", registerUserRequestDto.getLastName());
        assertEquals("0000", registerUserRequestDto.getPhoneNumber());
        assertEquals("example@app.com", registerUserRequestDto.getEmail());
        assertEquals("password", registerUserRequestDto.getPassword());
    }

    @Test
    @DisplayName("register user request")
    public void registerUserRequestInitialisedWithConstructor() {
        RegisterUserRequestDTO registerUserRequestDto = new RegisterUserRequestDTO("John", "Doe", "0000", dob, date);

        assertEquals("John", registerUserRequestDto.getFirstName());
        assertEquals("Doe", registerUserRequestDto.getLastName());
        assertEquals("0000", registerUserRequestDto.getPhoneNumber());
    }
}
