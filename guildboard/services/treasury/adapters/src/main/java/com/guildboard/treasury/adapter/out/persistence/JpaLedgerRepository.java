package com.guildboard.treasury.adapter.out.persistence;

import java.util.List;

import com.guildboard.treasury.application.port.out.LedgerRepository;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.LedgerEntry;
import com.guildboard.treasury.domain.QuestId;

import lombok.AllArgsConstructor;

/**
 * JpaLedgerRepository
 */
@AllArgsConstructor 
public class JpaLedgerRepository implements LedgerRepository{

    private final LedgerJpaRepository repo;

    @Override
    public boolean existsByQuestId(QuestId questId) {
        return repo.existsByQuestId(questId.value());
    }

    @Override
    public void save(LedgerEntry entry) {
        repo.save(LedgerEntryPersistenceMapper.toEntity(entry));
    }

    @Override
    public List<LedgerEntry> findByAdventurerId(AdventurerId adventurerId) {
        return repo.findByAdventurerId(adventurerId.value()).stream()
            .map(LedgerEntryPersistenceMapper::toDomain).toList();
    }

    
}
