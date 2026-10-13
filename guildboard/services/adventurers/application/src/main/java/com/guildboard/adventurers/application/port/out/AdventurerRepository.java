package com.guildboard.adventurers.application.port.out;

import java.util.Optional;

import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.AdventurerId;

/**
 * AdventurerRepository
 */
public interface AdventurerRepository {
    Optional<Adventurer> findById(AdventurerId id);

    void save(Adventurer adventurer);
}
