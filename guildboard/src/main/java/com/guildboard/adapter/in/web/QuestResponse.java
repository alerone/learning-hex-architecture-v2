package com.guildboard.adapter.in.web;

import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestStatus;
import com.guildboard.domain.Rank;

/**
 * QuestResponse
 */
public record QuestResponse(String id, String title, int reward, Rank requiredRank, QuestStatus status) {
    public static QuestResponse from(Quest quest) {
        return new QuestResponse(
                quest.getId().value(),
                quest.getTitle(),
                quest.getReward().gold(),
                quest.getRequiredRank(),
                quest.getStatus());
    }
}
