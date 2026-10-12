package com.guildboard.application.service;

import java.time.Clock;

import com.guildboard.application.port.in.AcceptQuestCommand;
import com.guildboard.application.port.in.AcceptQuestUseCase;
import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.domain.Adventurer;
import com.guildboard.domain.DomainError;
import com.guildboard.domain.DomainException;
import com.guildboard.domain.Quest;

import lombok.RequiredArgsConstructor;

/**
 * AcceptQuestService
 */
@RequiredArgsConstructor 
public class AcceptQuestService implements AcceptQuestUseCase {
    private final QuestRepository quests;
    private final AdventurerRepository adventurers;
    private final Clock clock;

    @Override
    public Quest accept(AcceptQuestCommand command) {
        Quest quest = quests.findById(command.quest()).orElseThrow(() ->
                new DomainException(new DomainError.QuestNotFound(command.quest())));

        Adventurer adventurer = adventurers.findById(command.adventurer()).orElseThrow(() ->
                new DomainException(new DomainError.AdventurerNotFound(command.adventurer())));

        quest.requireNotExpired(clock);
        int active = quests.countActiveBy(command.adventurer());
        quest.accept(adventurer.getId(), adventurer.getRank(), active);
        quests.save(quest);
        return quest;
    }
}
