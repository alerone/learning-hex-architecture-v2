package com.guildboard.treasury.adapter.out.memory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.guildboard.treasury.application.port.out.LedgerRepository;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.LedgerEntry;
import com.guildboard.treasury.domain.QuestId;

/**
 * InMemoryLedgerEntryRepository
 */
public class InMemoryLedgerRepository implements LedgerRepository{
    private final List<LedgerEntry> ledgers = new CopyOnWriteArrayList<>();

    @Override
    public boolean existsByQuestId(QuestId questId) {
        return ledgers.stream()
            .anyMatch(e -> e.questId().equals(questId));
    }

    @Override
    public void save(LedgerEntry entry) {
        ledgers.add(entry);
    }

    @Override
    public List<LedgerEntry> findByAdventurerId(AdventurerId adventurerId) {
        return ledgers.stream()
            .filter(e -> e.adventurerId().equals(adventurerId)).toList();
    }

    
}
