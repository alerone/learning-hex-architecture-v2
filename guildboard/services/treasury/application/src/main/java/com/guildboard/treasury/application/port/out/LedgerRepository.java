package com.guildboard.treasury.application.port.out;

import java.util.List;

import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.LedgerEntry;
import com.guildboard.treasury.domain.QuestId;

/**
 * LedgerRepository
 */
public interface LedgerRepository {

    boolean existsByQuestId(QuestId questId);

    void save(LedgerEntry entry);

    List<LedgerEntry> findByAdventurerId(AdventurerId adventurerId);
}

