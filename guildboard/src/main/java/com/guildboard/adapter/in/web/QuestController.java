package com.guildboard.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guildboard.application.port.in.AcceptQuestCommand;
import com.guildboard.application.port.in.AcceptQuestUseCase;
import com.guildboard.application.port.in.CompleteQuestCommand;
import com.guildboard.application.port.in.CompleteQuestUseCase;
import com.guildboard.application.port.in.GetQuestUseCase;
import com.guildboard.application.port.in.PublishQuestCommand;
import com.guildboard.application.port.in.PublishQuestUseCase;
import com.guildboard.domain.AdventurerId;
import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;
import com.guildboard.domain.Reward;

import lombok.RequiredArgsConstructor;

/**
 * QuestController
 */
@RestController
@RequestMapping("/quests")
@RequiredArgsConstructor
public class QuestController {

    private final PublishQuestUseCase publishQuest;
    private final GetQuestUseCase getQuest;
    private final AcceptQuestUseCase acceptQuest;
    private final CompleteQuestUseCase completeQuest;

    @PostMapping
    public ResponseEntity<QuestResponse> publish(
            @RequestBody PublishQuestRequest request) {
        PublishQuestCommand command = new PublishQuestCommand(
                request.title(), new Reward(request.reward()), request.requiredRank());
        Quest quest = publishQuest.publish(command);

        return ResponseEntity.created(URI.create("/quests/" + quest.getId().value()))
                .body(QuestResponse.from(quest));
    }

    @GetMapping("/{id}")
    ResponseEntity<QuestResponse> findById(@PathVariable("id") String id) {
        return getQuest.get(new QuestId(id))
                .map(QuestResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/accept")
    public QuestResponse accept(@PathVariable String id,
            @RequestBody AcceptQuestRequest request) {
        Quest quest = acceptQuest.accept(new AcceptQuestCommand(new QuestId(id),
                new AdventurerId(request.adventurerId())));
        return QuestResponse.from(quest);
    }

    @PostMapping("/{id}/complete")
    public QuestResponse complete(@PathVariable String id) {
        Quest quest = completeQuest.complete(new CompleteQuestCommand(new QuestId(id)));
        return QuestResponse.from(quest);
    }

}
