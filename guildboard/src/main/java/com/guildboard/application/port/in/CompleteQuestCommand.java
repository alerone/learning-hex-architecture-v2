package com.guildboard.application.port.in;

import com.guildboard.domain.QuestId;

/**
 * CompleteQuestCommand
 */
public record CompleteQuestCommand(QuestId quest) { }
