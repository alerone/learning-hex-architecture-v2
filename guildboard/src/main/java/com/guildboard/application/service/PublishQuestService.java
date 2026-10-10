package com.guildboard.application.service;

import java.util.UUID;

import com.guildboard.application.port.in.PublishQuestCommand;
import com.guildboard.application.port.in.PublishQuestUseCase;
import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;

import lombok.RequiredArgsConstructor;

/**
 * PublishQuestService
 */
@RequiredArgsConstructor 
public class PublishQuestService implements PublishQuestUseCase {
    private final QuestRepository quests;

    @Override
    public Quest publish(PublishQuestCommand command) {
        Quest quest = Quest.draft(
               new QuestId(UUID.randomUUID().toString()),
               command.title(), command.reward(), command.requiredRank());
        quest.publish();
        quests.save(quest);
        return quest;
    }
}
