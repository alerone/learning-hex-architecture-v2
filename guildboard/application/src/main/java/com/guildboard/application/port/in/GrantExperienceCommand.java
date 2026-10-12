package com.guildboard.application.port.in;

import com.guildboard.domain.AdventurerId;

/**
 * GrantExperienceCommand
 */
public record GrantExperienceCommand(AdventurerId adventurer, int points) { }
