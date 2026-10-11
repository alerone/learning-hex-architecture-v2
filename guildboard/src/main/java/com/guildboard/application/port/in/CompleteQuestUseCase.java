package com.guildboard.application.port.in;

import com.guildboard.domain.Quest;

/**
 * CompleteQuestUseCase
 */
public interface CompleteQuestUseCase {

    Quest complete(CompleteQuestCommand command);
}
