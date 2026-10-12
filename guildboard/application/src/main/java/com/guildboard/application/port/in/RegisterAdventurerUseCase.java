package com.guildboard.application.port.in;

import com.guildboard.domain.Adventurer;

/**
 * RegisterAdventurerUseCase
 */
public interface RegisterAdventurerUseCase {

    Adventurer register(RegisterAdventurerCommand command);
}
