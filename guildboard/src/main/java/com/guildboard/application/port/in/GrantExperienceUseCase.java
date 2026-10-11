package com.guildboard.application.port.in;

import com.guildboard.domain.Adventurer;

/**
 * GrantExperienceUseCase
 */
public interface GrantExperienceUseCase {

    Adventurer grant(GrantExperienceCommand command);
}
