package com.guildboard.application.port.in;

import java.time.Duration;

import com.guildboard.domain.Rank;
import com.guildboard.domain.Reward;

/**
 * PublishQuestCommand
 */
public record PublishQuestCommand(String title, Reward reward, Rank requiredRank, Duration validFor) { }
