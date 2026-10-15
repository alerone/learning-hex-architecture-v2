package com.guildboard.treasury.adapter.in.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * QuestCompleted
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record QuestCompleted(
        String questId, String adventurerId, int reward) {
}
