package com.guildboard.application.service;

import java.util.Optional;

import com.guildboard.application.port.in.GetQuestUseCase;
import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;

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
