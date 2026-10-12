package com.guildboard.adapter.in.web.dto;

import com.guildboard.domain.QuestStatus;
import com.guildboard.domain.Rank;

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
