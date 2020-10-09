package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.entities.NotificationEmail;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.service.MailService;
import com.morlimoore.piggybankapi.util.MailContentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    public MailServiceImpl(JavaMailSender mailSender,
                           MailContentBuilder mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Override
    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("signup@piggybankplc.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            throw new CustomException("Exception occured when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
