package com.guildboard.quests.application.port.out;

import com.guildboard.quests.domain.AdventurerId;

/**
 * NotificationSender
 */
public interface NotificationSender {

    void send(AdventurerId recipient, String message);
}
