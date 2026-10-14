package com.guildboard.treasury.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LedgerJpaRepository
 */
public interface LedgerJpaRepository extends JpaRepository<LedgerEntryEntity, String> {
    boolean existsByQuestId(String questId);

    List<LedgerEntryEntity> findByAdventurerId(String adventurerId);
}
