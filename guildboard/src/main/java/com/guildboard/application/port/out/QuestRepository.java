package com.guildboard.application.port.out;

import java.util.Optional;

import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;

/**
 * QuestRepository
 */
public interface QuestRepository {
    Optional<Quest> findById(QuestId id);

    void save(Quest quest);
}
