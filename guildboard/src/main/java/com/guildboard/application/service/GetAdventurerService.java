package com.guildboard.application.service;

import java.util.Optional;

import com.guildboard.application.port.in.GetAdventurerUseCase;
import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.domain.Adventurer;
import com.guildboard.domain.AdventurerId;

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
