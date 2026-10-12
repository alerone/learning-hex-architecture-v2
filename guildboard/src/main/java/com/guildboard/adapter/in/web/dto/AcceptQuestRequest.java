package com.guildboard.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * AcceptQuestRequest
 */
public record AcceptQuestRequest(@NotBlank String adventurerId) {
}
