package com.guildboard.adapter.out.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.domain.Adventurer;
import com.guildboard.domain.AdventurerId;

/**
 * InMemoryAdventurerRepository
 */
public class InMemoryAdventurerRepository implements AdventurerRepository{
    private final Map<AdventurerId, Adventurer> adventurers = new ConcurrentHashMap<>();

    @Override
    public Optional<Adventurer> findById(AdventurerId id) {
        return Optional.ofNullable(adventurers.get(id));
    }

    @Override
    public void save(Adventurer adventurer) {
        adventurers.put(adventurer.getId(), adventurer);
    }
}
