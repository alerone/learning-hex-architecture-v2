package com.guildboard.application.service;

import com.guildboard.application.port.in.GrantExperienceCommand;
import com.guildboard.application.port.in.GrantExperienceUseCase;
import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.domain.Adventurer;
import com.guildboard.domain.DomainError;
import com.guildboard.domain.DomainException;

import lombok.RequiredArgsConstructor;

/**
 * GrantExperienceService
 */
@RequiredArgsConstructor 
public class GrantExperienceService implements GrantExperienceUseCase {

    private final AdventurerRepository adventurerRepository;

    @Override
    public Adventurer grant(GrantExperienceCommand command) {
        Adventurer adventurer = adventurerRepository.findById(command.adventurer()).orElseThrow(() ->
                new DomainException(new DomainError.AdventurerNotFound(command.adventurer())));

        adventurer.gainExperience(command.points());
        adventurerRepository.save(adventurer);
        return adventurer;
    }
}
