package com.guildboard.adapter.out.notification;

import com.guildboard.application.port.out.NotificationSender;
import com.guildboard.domain.AdventurerId;

import lombok.extern.slf4j.Slf4j;

/**
 * LogNotificationSender
 */
@Slf4j 
public class LogNotificationSender implements NotificationSender {

    @Override
    public void send(AdventurerId recipient, String message) {
        log.info("notification to {}: {}", recipient.value(), message);
    }

    
}
