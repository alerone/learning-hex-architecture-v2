package com.guildboard.treasury.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * PayRewardRequest
 */
public record PayRewardRequest(
        @NotBlank String questId,
        @NotBlank String adventurerId,
        @Positive int amount) {
}
