package com.morlimoore.piggybankapi.service.impl;

import com.mashape.unirest.http.Unirest;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${application.mail-client.password}")
    private String password;

    @Value("${application.mail-client.domain}")
    private String domain;

    @Override
    public void sendActivationMail(String email, String token) {
        try {
            Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages")
                    .basicAuth("api", password)
                    .queryString("from", "SAVE-SAFE Admin <admin@savesafe.com>")
                    .queryString("to", email)
                    .queryString("subject", "Welcome to Save Safe")
                    .queryString("text", "Thank you for signing up to the SAVE-SAFE App. \n" +
                            "Please click on, or copy and paste the below URI in your browser, to activate your account: \n" +
                            "https://localhost:8443/api/v1/auth/accountVerification/" + token)
                    .asJson();
        } catch(Exception e) {
            logger.error("Check that the email client password is correct.");
            throw new CustomException("Please contact the administrator.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
