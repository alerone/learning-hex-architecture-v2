package com.guildboard.quests.application.port.in;

import com.guildboard.quests.domain.Quest;

/**
 * PublishQuestUseCase
 */
public interface PublishQuestUseCase {

    Quest publish(PublishQuestCommand command);
}
