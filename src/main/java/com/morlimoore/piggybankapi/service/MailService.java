package com.morlimoore.piggybankapi.service;

import com.morlimoore.piggybankapi.entities.NotificationEmail;

public interface MailService {
    void sendMail(NotificationEmail notificationEmail);
}
