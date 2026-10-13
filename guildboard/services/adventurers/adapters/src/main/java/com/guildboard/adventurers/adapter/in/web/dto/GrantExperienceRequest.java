package com.guildboard.adventurers.adapter.in.web.dto;

import jakarta.validation.constraints.Positive;

/**
 * GrantExperienceRequest
 */
public record GrantExperienceRequest(@Positive int points) {
}
