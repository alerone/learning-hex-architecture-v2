package com.guildboard.quests.application.port.out;

import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.Rank;

/**
 * AdventurerView
 */
public record AdventurerView(AdventurerId id, Rank rank) {
}
