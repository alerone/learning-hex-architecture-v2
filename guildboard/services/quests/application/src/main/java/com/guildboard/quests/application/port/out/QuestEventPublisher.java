package com.guildboard.quests.application.port.out;

import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.QuestId;
import com.guildboard.quests.domain.Reward;

/**
 * QuestEventPublisher
 */
public interface QuestEventPublisher {

    void questCompleted(
            QuestId questId, AdventurerId adventurerId, Reward reward);
}
