package com.guildboard.quests.application.service;

import java.time.Clock;

import com.guildboard.quests.application.port.in.AcceptQuestCommand;
import com.guildboard.quests.application.port.in.AcceptQuestUseCase;
import com.guildboard.quests.application.port.out.AdventurerDirectory;
import com.guildboard.quests.application.port.out.AdventurerView;
import com.guildboard.quests.application.port.out.QuestRepository;
import com.guildboard.quests.domain.DomainError;
import com.guildboard.quests.domain.DomainException;
import com.guildboard.quests.domain.Quest;

import lombok.RequiredArgsConstructor;

/**
 * AcceptQuestService
 */
@RequiredArgsConstructor 
public class AcceptQuestService implements AcceptQuestUseCase {
    private final QuestRepository quests;
    private final AdventurerDirectory adventurers;
    private final Clock clock;

    @Override
    public Quest accept(AcceptQuestCommand command) {
        Quest quest = quests.findById(command.quest()).orElseThrow(() ->
                new DomainException(new DomainError.QuestNotFound(command.quest())));

        AdventurerView adventurer = adventurers.findById(command.adventurer()).orElseThrow(() ->
                new DomainException(new DomainError.AdventurerNotFound(command.adventurer())));

        quest.requireNotExpired(clock);
        int active = quests.countActiveBy(command.adventurer());
        quest.accept(adventurer.id(), adventurer.rank(), active);
        quests.save(quest);
        return quest;
    }
}
