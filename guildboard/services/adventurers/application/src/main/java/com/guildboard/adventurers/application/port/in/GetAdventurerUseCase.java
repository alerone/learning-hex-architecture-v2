package com.guildboard.adventurers.application.port.in;

import java.util.Optional;

import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.AdventurerId;

/**
 * GetAdventurerUseCase
 */
public interface GetAdventurerUseCase {

    Optional<Adventurer> get(AdventurerId id);
}
