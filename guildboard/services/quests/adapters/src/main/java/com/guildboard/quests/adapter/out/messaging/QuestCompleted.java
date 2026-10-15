package com.guildboard.quests.adapter.out.messaging;

/**
 * QuestCompleted
 */
public record QuestCompleted(
        String questId, String adventurerId, int reward) {
}
