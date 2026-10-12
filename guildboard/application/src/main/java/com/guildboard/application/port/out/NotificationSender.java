package com.guildboard.application.port.out;

import com.guildboard.domain.AdventurerId;

/**
 * NotificationSender
 */
public interface NotificationSender {

    void send(AdventurerId recipient, String message);
}
