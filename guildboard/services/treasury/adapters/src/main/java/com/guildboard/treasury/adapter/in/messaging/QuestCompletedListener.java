package com.guildboard.treasury.adapter.in.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.guildboard.treasury.application.port.in.PayRewardCommand;
import com.guildboard.treasury.application.port.in.PayRewardUseCase;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.DomainError;
import com.guildboard.treasury.domain.DomainException;
import com.guildboard.treasury.domain.Gold;
import com.guildboard.treasury.domain.QuestId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * QuestCompletedListener
 */
@Slf4j 
@Component 
@RequiredArgsConstructor 
public class QuestCompletedListener {

    private final PayRewardUseCase payReward;

    @KafkaListener(
     topics = "${guildboard.messaging.topic}", groupId = "treasury")
    public void onQuestCompleted(QuestCompleted message) {
        PayRewardCommand command = new PayRewardCommand(
                    new QuestId(message.questId()),
                    new AdventurerId(message.adventurerId()),
                    new Gold(message.reward()));
        try {
            payReward.pay(command);
        } catch (DomainException e) {
            if (!(e.getError() instanceof DomainError.QuestAlreadyPaid)) {
                throw e;
            }
            log.info("quest {} already paid, message ignored",
                    message.questId());
        }
    }
}
