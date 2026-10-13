package com.guildboard.quests.application.port.in;

import com.guildboard.quests.domain.Quest;

/**
 * AcceptQuestUseCase
 */
public interface AcceptQuestUseCase {

    Quest accept(AcceptQuestCommand command);
}
