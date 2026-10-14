package com.guildboard.treasury.application.port.in;

import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.Gold;
import com.guildboard.treasury.domain.QuestId;

/**
 * PayRewardCommand
 */
public record PayRewardCommand(QuestId questId, AdventurerId adventurerId, Gold amount) {
}
