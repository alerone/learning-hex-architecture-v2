package com.guildboard.quests.application.port.in;

import java.time.Duration;

import com.guildboard.quests.domain.Rank;
import com.guildboard.quests.domain.Reward;

/**
 * PublishQuestCommand
 */
public record PublishQuestCommand(String title, Reward reward, Rank requiredRank, Duration validFor) { }
