package com.morlimoore.piggybankapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
public class PiggybankApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiggybankApiApplication.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
