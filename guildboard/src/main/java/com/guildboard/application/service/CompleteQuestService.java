package com.guildboard.application.service;

import com.guildboard.application.port.in.CompleteQuestCommand;
import com.guildboard.application.port.in.CompleteQuestUseCase;
import com.guildboard.application.port.in.GrantExperienceCommand;
import com.guildboard.application.port.in.GrantExperienceUseCase;
import com.guildboard.application.port.out.NotificationSender;
import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.domain.AdventurerId;
import com.guildboard.domain.DomainError;
import com.guildboard.domain.DomainException;
import com.guildboard.domain.Quest;

import lombok.RequiredArgsConstructor;

/**
 * CompleteQuestService
 */
@RequiredArgsConstructor 
public class CompleteQuestService implements CompleteQuestUseCase {

    private final QuestRepository quests;
    private final GrantExperienceUseCase grantExperience;
    private final NotificationSender notifications;

    @Override
    public Quest complete(CompleteQuestCommand command) {
        Quest quest = quests.findById(command.quest()).orElseThrow(() -> 
                new DomainException(new DomainError.QuestNotFound(command.quest())));

        quest.complete();
        AdventurerId assignee = quest.getAssignedTo().orElseThrow(() -> 
                new IllegalStateException("accepted quest without assignee: " + quest.getId().value()));
        quests.save(quest);
        int points = quest.getReward().gold();
        if (points > 0) {
            grantExperience.grant(new GrantExperienceCommand(assignee, points));
        }

        notifications.send(assignee, "quest completed: " + quest.getTitle());

        return quest;
    }
}
