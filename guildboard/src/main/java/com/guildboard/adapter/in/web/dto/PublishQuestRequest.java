package com.guildboard.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * PublishQuestRequest
 */
public record PublishQuestRequest(
        @NotBlank @Size(max = 80) String title,
        @NotNull @Positive  int reward,
        @NotBlank String requiredRank,
        @Positive Integer validForMinutes) {
}
