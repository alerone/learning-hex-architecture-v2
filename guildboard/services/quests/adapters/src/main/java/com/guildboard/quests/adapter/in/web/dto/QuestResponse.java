package com.guildboard.quests.adapter.in.web.dto;

import com.guildboard.quests.domain.QuestStatus;
import com.guildboard.quests.domain.Rank;

/**
 * QuestResponse
 */
public record QuestResponse(
        String id,
        String title,
        int reward,
        Rank requiredRank,
        QuestStatus status,
        String assignedto,
        String expiresAt) {
}
