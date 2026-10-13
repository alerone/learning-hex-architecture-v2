package com.guildboard.adapter.out.persistence;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.domain.Adventurer;
import com.guildboard.domain.AdventurerId;

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
