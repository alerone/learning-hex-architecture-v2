package com.guildboard.quests.application.port.out;

import java.util.Optional;

import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;

/**
 * QuestRepository
 */
public interface QuestRepository {
    Optional<Quest> findById(QuestId id);

    void save(Quest quest);

    int countActiveBy(AdventurerId adventurerId);
}
