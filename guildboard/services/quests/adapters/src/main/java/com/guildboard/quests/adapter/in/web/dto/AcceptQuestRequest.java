package com.guildboard.quests.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * AcceptQuestRequest
 */
public record AcceptQuestRequest(@NotBlank String adventurerId) {
}
