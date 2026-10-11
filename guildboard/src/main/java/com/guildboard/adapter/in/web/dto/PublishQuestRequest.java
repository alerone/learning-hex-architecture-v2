package com.guildboard.adapter.in.web.dto;

import com.guildboard.domain.Rank;

/**
 * PublishQuestRequest
 */
public record PublishQuestRequest(String title, int reward, Rank requiredRank) {
}
