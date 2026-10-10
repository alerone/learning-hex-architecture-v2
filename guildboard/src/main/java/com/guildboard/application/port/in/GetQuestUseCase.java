package com.guildboard.application.port.in;

import java.util.Optional;

import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;

/**
 * GetQuestUseCase
 */
public interface GetQuestUseCase {

    Optional<Quest> get(QuestId id);
}
