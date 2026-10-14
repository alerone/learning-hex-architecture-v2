package com.guildboard.treasury.adapter.out.persistence;

import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.Gold;
import com.guildboard.treasury.domain.LedgerEntry;
import com.guildboard.treasury.domain.QuestId;

/**
 * LedgerEntryPersistenceMapper
 */
final class LedgerEntryPersistenceMapper {

    private LedgerEntryPersistenceMapper() {}

    static LedgerEntryEntity toEntity(LedgerEntry entry) {
        return new LedgerEntryEntity(
                entry.id(),
                entry.questId().value(),
                entry.adventurerId().value(),
                entry.amount().coins(),
                entry.createdAt());
    }

    static LedgerEntry toDomain(LedgerEntryEntity entity) {
        return new LedgerEntry(entity.getId(),
                new QuestId(entity.getQuestId()), 
                new AdventurerId(entity.getAdventurerId()), 
                new Gold(entity.getAmount()), entity.getCreatedAt());
    }
    
}
