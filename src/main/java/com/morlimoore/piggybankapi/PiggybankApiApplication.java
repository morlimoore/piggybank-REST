package com.morlimoore.piggybankapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

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
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    JavaMailSender mailSender() {
        return new JavaMailSender() {
            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage mimeMessage) throws MailException {
            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {
            }

            @Override
            public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
            }

            @Override
            public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
            }

            @Override
            public void send(SimpleMailMessage simpleMailMessage) throws MailException {
            }

            @Override
            public void send(SimpleMailMessage... simpleMailMessages) throws MailException {
            }
        };
    }
}
