package com.guildboard.adventurers.application.service;

import java.util.Optional;

import com.guildboard.adventurers.application.port.in.GetAdventurerUseCase;
import com.guildboard.adventurers.application.port.out.AdventurerRepository;
import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * GetAdventurerService
 */
@RequiredArgsConstructor 
public class GetAdventurerService implements GetAdventurerUseCase{

    private final AdventurerRepository adventurers;

    @Override
    public Optional<Adventurer> get(AdventurerId id) {
        return adventurers.findById(id);
    }
}
