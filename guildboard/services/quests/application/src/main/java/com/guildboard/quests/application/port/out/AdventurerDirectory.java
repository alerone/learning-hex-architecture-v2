package com.guildboard.quests.application.port.out;

import java.util.Optional;

import com.guildboard.quests.domain.AdventurerId;

/**
 * AdventurerDirectory
 */
public interface AdventurerDirectory {

    Optional<AdventurerView> findById(AdventurerId id);
}
