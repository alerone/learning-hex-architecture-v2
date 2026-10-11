package com.guildboard.application.port.in;

import com.guildboard.domain.Quest;

/**
 * AcceptQuestUseCase
 */
public interface AcceptQuestUseCase {

    Quest accept(AcceptQuestCommand command);
}
