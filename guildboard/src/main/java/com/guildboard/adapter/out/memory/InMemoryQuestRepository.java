package com.guildboard.adapter.out.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.domain.AdventurerId;
import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;

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
            .toList().size();
    }

    
}
