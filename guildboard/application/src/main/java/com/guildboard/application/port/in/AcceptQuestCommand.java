package com.guildboard.application.port.in;

import com.guildboard.domain.AdventurerId;
import com.guildboard.domain.QuestId;

/**
 * AcceptQuestCommand
 */
public record AcceptQuestCommand(QuestId quest, AdventurerId adventurer) { }
