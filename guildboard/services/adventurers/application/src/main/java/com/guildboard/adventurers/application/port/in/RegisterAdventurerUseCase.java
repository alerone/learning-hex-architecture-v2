package com.guildboard.adventurers.application.port.in;

import com.guildboard.adventurers.domain.Adventurer;

/**
 * RegisterAdventurerUseCase
 */
public interface RegisterAdventurerUseCase {

    Adventurer register(RegisterAdventurerCommand command);
}
