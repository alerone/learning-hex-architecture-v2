package com.guildboard.quests.application.service;

import java.util.Optional;

import com.guildboard.quests.application.port.in.GetQuestUseCase;
import com.guildboard.quests.application.port.out.QuestRepository;
import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;

import lombok.RequiredArgsConstructor;

/**
 * GetQuestService
 */
@RequiredArgsConstructor 
public class GetQuestService implements GetQuestUseCase {
    private final QuestRepository quests;

    @Override
    public Optional<Quest> get(QuestId id) {
        return quests.findById(id);
    }

    
}
