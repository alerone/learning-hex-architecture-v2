package com.guildboard.quests.adapter.out.persistence;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.guildboard.quests.application.port.out.QuestRepository;
import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;
import com.guildboard.quests.domain.QuestStatus;

import lombok.RequiredArgsConstructor;

/**
 * JpaQuestRepository
 */
@RequiredArgsConstructor 
@Transactional 
public class JpaQuestRepository implements QuestRepository {
    private final QuestJpaRepository jpa;

    @Override
    @Transactional(readOnly = true) 
    public Optional<Quest> findById(QuestId id) {
        return jpa.findById(id.value())
            .map(QuestPersistenceMapper::toDomain);
    }

    @Override
    public void save(Quest quest) {
        jpa.save(QuestPersistenceMapper.toEntity(quest));
    }

    @Override
    @Transactional(readOnly = true) 
    public int countActiveBy(AdventurerId adventurerId) {
        return Math.toIntExact(jpa.countByAdventurerIdAndStatus(
                    adventurerId.value(), QuestStatus.ACCEPTED.name()));
    }


}
