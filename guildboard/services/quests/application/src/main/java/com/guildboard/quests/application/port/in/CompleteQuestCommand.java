package com.guildboard.quests.application.port.in;

import com.guildboard.quests.domain.QuestId;

/**
 * CompleteQuestCommand
 */
public record CompleteQuestCommand(QuestId quest) { }
