package com.morlimoore.piggybankapi.service;

public interface MailService {

    void sendActivationMail(String email, String token);

}
