package com.guildboard.quests.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * QuestJpaRepository
 */
public interface QuestJpaRepository extends JpaRepository<QuestEntity, String> {
    long countByAdventurerIdAndStatus(String adventurerId, String status);
}
