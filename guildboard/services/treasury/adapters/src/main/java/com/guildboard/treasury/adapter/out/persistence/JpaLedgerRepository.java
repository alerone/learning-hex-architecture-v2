package com.guildboard.treasury.adapter.out.persistence;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.guildboard.treasury.application.port.out.LedgerRepository;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.DomainError;
import com.guildboard.treasury.domain.DomainException;
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
        try {
            repo.saveAndFlush(LedgerEntryPersistenceMapper.toEntity(entry));
        } catch (DataIntegrityViolationException e) {
            throw new DomainException(new DomainError.QuestAlreadyPaid(entry.questId()));
        }
    }

    @Override
    public List<LedgerEntry> findByAdventurerId(AdventurerId adventurerId) {
        return repo.findByAdventurerId(adventurerId.value()).stream()
            .map(LedgerEntryPersistenceMapper::toDomain).toList();
    }

    
}
