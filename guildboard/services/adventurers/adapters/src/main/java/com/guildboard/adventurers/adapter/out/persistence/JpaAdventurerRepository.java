package com.guildboard.adventurers.adapter.out.persistence;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.guildboard.adventurers.application.port.out.AdventurerRepository;
import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * JpaAdventurerRepository
 */
@RequiredArgsConstructor  
@Transactional 
public class JpaAdventurerRepository implements AdventurerRepository{
    private final AdventurerJpaRepository jpa;

    @Override
    @Transactional(readOnly = true)
    public Optional<Adventurer> findById(AdventurerId id) {
        return jpa.findById(id.value()).map(AdventurerPersistenceMapper::toDomain);
    }

    @Override
    public void save(Adventurer adventurer) {
        jpa.save(AdventurerPersistenceMapper.toEntity(adventurer));
    }

    
}
