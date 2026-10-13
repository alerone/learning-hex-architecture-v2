package com.guildboard.quests.application.service;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import com.guildboard.quests.application.port.in.PublishQuestCommand;
import com.guildboard.quests.application.port.in.PublishQuestUseCase;
import com.guildboard.quests.application.port.out.QuestRepository;
import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;

import lombok.RequiredArgsConstructor;

/**
 * PublishQuestService
 */
@RequiredArgsConstructor 
public class PublishQuestService implements PublishQuestUseCase {
    private final QuestRepository quests;
    private final Clock clock;

    @Override
    public Quest publish(PublishQuestCommand command) {
        Instant expiresAt = command.validFor() == null 
            ? null 
            : clock.instant().plus(command.validFor());
        Quest quest = Quest.draft(
               new QuestId(UUID.randomUUID().toString()),
               command.title(), command.reward(), command.requiredRank(), 
               expiresAt);
        quest.publish();
        quests.save(quest);
        return quest;
    }
}
