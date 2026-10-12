package com.guildboard.adapter.out.persistence;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.domain.AdventurerId;
import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;
import com.guildboard.domain.QuestStatus;

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
