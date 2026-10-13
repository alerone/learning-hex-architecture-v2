package com.guildboard.quests.application.port.in;

import java.util.Optional;

import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;

/**
 * GetQuestUseCase
 */
public interface GetQuestUseCase {

    Optional<Quest> get(QuestId id);
}
