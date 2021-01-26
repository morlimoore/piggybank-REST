package com.morlimoore.piggybankapi.service.impl;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.morlimoore.piggybankapi.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Value("${application.mail-client.password}")
    private String password;

    @Value("${application.mail-client.domain}")
    private String domain;

    @Override
    public void sendActivationMail(String email, String token) throws UnirestException {
        Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
            .basicAuth("api", password)
            .queryString("from", "SAVE-SAFE Admin <admin@savesafe.com>")
            .queryString("to", email)
            .queryString("subject", "Welcome to Save Safe")
            .queryString("text", "Thank you for signing up to the SAVE-SAFE App. \n" +
                    "Please click on, or copy and paste the below URI in your browser, to activate your account: \n" +
                    "https://localhost:8443/api/v1/auth/accountVerification/" + token)
            .asJson();
    }
}
