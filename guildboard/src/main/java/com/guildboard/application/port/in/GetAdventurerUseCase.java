package com.guildboard.application.port.in;

import java.util.Optional;

import com.guildboard.domain.Adventurer;
import com.guildboard.domain.AdventurerId;

/**
 * GetAdventurerUseCase
 */
public interface GetAdventurerUseCase {

    Optional<Adventurer> get(AdventurerId id);
}
