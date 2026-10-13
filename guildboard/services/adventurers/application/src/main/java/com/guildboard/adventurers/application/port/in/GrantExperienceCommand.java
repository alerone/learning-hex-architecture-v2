package com.guildboard.adventurers.application.port.in;

import com.guildboard.adventurers.domain.AdventurerId;

/**
 * GrantExperienceCommand
 */
public record GrantExperienceCommand(AdventurerId adventurer, int points) { }
