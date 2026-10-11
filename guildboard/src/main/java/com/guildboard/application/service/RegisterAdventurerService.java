package com.guildboard.application.service;

import java.util.UUID;

import com.guildboard.application.port.in.RegisterAdventurerUseCase;
import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.domain.Adventurer;
import com.guildboard.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * RegisterAdventurerService
 */
@RequiredArgsConstructor 
public class RegisterAdventurerService implements RegisterAdventurerUseCase{

    private final AdventurerRepository repo;

    @Override
    public Adventurer register(String name) {
        AdventurerId id = new AdventurerId(UUID.randomUUID().toString());
        Adventurer adventurer = Adventurer.register(id, name);
        repo.save(adventurer);
        return adventurer;
    }
}
