package com.guildboard.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guildboard.application.port.in.GetQuestUseCase;
import com.guildboard.application.port.in.PublishQuestCommand;
import com.guildboard.application.port.in.PublishQuestUseCase;
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
}
