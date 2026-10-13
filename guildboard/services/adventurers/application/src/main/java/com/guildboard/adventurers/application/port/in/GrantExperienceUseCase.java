package com.guildboard.adventurers.application.port.in;

import com.guildboard.adventurers.domain.Adventurer;

/**
 * GrantExperienceUseCase
 */
public interface GrantExperienceUseCase {

    Adventurer grant(GrantExperienceCommand command);
}
