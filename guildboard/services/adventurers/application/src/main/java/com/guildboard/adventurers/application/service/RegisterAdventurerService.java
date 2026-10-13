package com.guildboard.adventurers.application.service;

import java.util.UUID;

import com.guildboard.adventurers.application.port.in.RegisterAdventurerCommand;
import com.guildboard.adventurers.application.port.in.RegisterAdventurerUseCase;
import com.guildboard.adventurers.application.port.out.AdventurerRepository;
import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * RegisterAdventurerService
 */
@RequiredArgsConstructor 
public class RegisterAdventurerService implements RegisterAdventurerUseCase{

    private final AdventurerRepository repo;

    @Override
    public Adventurer register(RegisterAdventurerCommand command) {
        AdventurerId id = new AdventurerId(UUID.randomUUID().toString());
        Adventurer adventurer = Adventurer.register(id, command.name());
        repo.save(adventurer);
        return adventurer;
    }
}
