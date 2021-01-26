package com.morlimoore.piggybankapi.service;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface MailService {

    void sendActivationMail(String email, String token) throws UnirestException;

}
