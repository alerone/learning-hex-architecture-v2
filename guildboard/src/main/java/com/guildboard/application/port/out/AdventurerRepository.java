package com.guildboard.application.port.out;

import java.util.Optional;

import com.guildboard.domain.Adventurer;
import com.guildboard.domain.AdventurerId;

/**
 * AdventurerRepository
 */
public interface AdventurerRepository {
    Optional<Adventurer> findById(AdventurerId id);

    void save(Adventurer adventurer);
}
