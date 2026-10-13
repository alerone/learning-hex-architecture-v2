package com.guildboard.quests.adapter.out.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.guildboard.quests.application.port.out.QuestRepository;
import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;
import com.guildboard.quests.domain.QuestStatus;

/**
 * InMemoryQuestRepository
 */
public class InMemoryQuestRepository implements QuestRepository {
    private final Map<QuestId, Quest> quests = new ConcurrentHashMap<>();

    @Override
    public Optional<Quest> findById(QuestId id) {
        return Optional.ofNullable(quests.get(id));
    }

    @Override
    public void save(Quest quest) {
        quests.put(quest.getId(), quest);
    }

    @Override
    public int countActiveBy(AdventurerId adventurerId) {
        return quests.values().stream()
            .filter(q -> q.getAssignedTo().isPresent())
            .filter(q -> q.getAssignedTo().get().equals(adventurerId))
            .filter(q -> q.getStatus() == QuestStatus.ACCEPTED)
            .toList().size();
    }

    
}
