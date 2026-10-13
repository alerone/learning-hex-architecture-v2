package com.guildboard.quests.application.port.in;

import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.QuestId;

/**
 * AcceptQuestCommand
 */
public record AcceptQuestCommand(QuestId quest, AdventurerId adventurer) { }
