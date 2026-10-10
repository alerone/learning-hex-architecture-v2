package com.guildboard.application.port.in;

import com.guildboard.domain.Quest;

/**
 * PublishQuestUseCase
 */
public interface PublishQuestUseCase {

    Quest publish(PublishQuestCommand command);
}
