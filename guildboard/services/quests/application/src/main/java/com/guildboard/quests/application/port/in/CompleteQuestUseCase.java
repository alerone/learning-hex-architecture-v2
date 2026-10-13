package com.guildboard.quests.application.port.in;

import com.guildboard.quests.domain.Quest;

/**
 * CompleteQuestUseCase
 */
public interface CompleteQuestUseCase {

    Quest complete(CompleteQuestCommand command);
}
