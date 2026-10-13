package com.guildboard.quests.application.service;

import com.guildboard.quests.application.port.in.CompleteQuestCommand;
import com.guildboard.quests.application.port.in.CompleteQuestUseCase;
import com.guildboard.quests.application.port.out.NotificationSender;
import com.guildboard.quests.application.port.out.QuestRepository;
import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.DomainError;
import com.guildboard.quests.domain.DomainException;
import com.guildboard.quests.domain.Quest;

import lombok.RequiredArgsConstructor;

/**
 * CompleteQuestService
 */
@RequiredArgsConstructor 
public class CompleteQuestService implements CompleteQuestUseCase {

    private final QuestRepository quests;
    private final NotificationSender notifications;

    @Override
    public Quest complete(CompleteQuestCommand command) {
        Quest quest = quests.findById(command.quest()).orElseThrow(() -> 
                new DomainException(new DomainError.QuestNotFound(command.quest())));

        quest.complete();
        AdventurerId assignee = quest.getAssignedTo().orElseThrow(() -> 
                new IllegalStateException("accepted quest without assignee: " + quest.getId().value()));
        quests.save(quest);

        notifications.send(assignee, "quest completed: " + quest.getTitle());

        return quest;
    }
}
