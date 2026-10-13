package com.guildboard.adventurers.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * RegisterAdventurerRequest
 */
public record RegisterAdventurerRequest(
        @NotBlank @Size(max = 50) String name) {
}
