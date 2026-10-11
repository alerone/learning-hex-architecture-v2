package com.guildboard.adapter.in.web.mapper;

import com.guildboard.adapter.in.web.dto.QuestResponse;
import com.guildboard.adapter.in.web.dto.AcceptQuestRequest;
import com.guildboard.adapter.in.web.dto.PublishQuestRequest;
import com.guildboard.application.port.in.AcceptQuestCommand;
import com.guildboard.application.port.in.CompleteQuestCommand;
import com.guildboard.application.port.in.PublishQuestCommand;
import com.guildboard.domain.AdventurerId;
import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;
import com.guildboard.domain.Reward;

/**
 * QuestWebMapper
 */
public final class QuestWebMapper {

    private QuestWebMapper() {}

    public static AcceptQuestCommand toAcceptCommand( 
            String questId,
            AcceptQuestRequest request) {
        return new AcceptQuestCommand(new QuestId(questId),
                new AdventurerId(request.adventurerId()));
    }

    public static PublishQuestCommand toPublishCommand(PublishQuestRequest rq) {
        return new PublishQuestCommand(rq.title(), new Reward(rq.reward()), rq.requiredRank());
    }

    public static CompleteQuestCommand toCompleteCommand(String id) {
        return new CompleteQuestCommand(new QuestId(id));
    }

    public static QuestResponse toResponse(Quest quest) {
        return new QuestResponse(
                quest.getId().value(),
                quest.getTitle(),
                quest.getReward().gold(),
                quest.getRequiredRank(),
                quest.getStatus());
    }
}
