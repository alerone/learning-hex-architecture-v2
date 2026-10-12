package com.guildboard.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guildboard.adapter.in.web.dto.AcceptQuestRequest;
import com.guildboard.adapter.in.web.dto.PublishQuestRequest;
import com.guildboard.adapter.in.web.dto.QuestResponse;
import com.guildboard.adapter.in.web.mapper.QuestWebMapper;
import com.guildboard.application.port.in.AcceptQuestUseCase;
import com.guildboard.application.port.in.CompleteQuestUseCase;
import com.guildboard.application.port.in.GetQuestUseCase;
import com.guildboard.application.port.in.PublishQuestCommand;
import com.guildboard.application.port.in.PublishQuestUseCase;
import com.guildboard.domain.Quest;
import com.guildboard.domain.QuestId;
import jakarta.validation.Valid;
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
            @Valid  @RequestBody PublishQuestRequest request) {
        PublishQuestCommand command = QuestWebMapper.toPublishCommand(request);
        Quest quest = publishQuest.publish(command);

        return ResponseEntity.created(URI.create("/quests/" + quest.getId().value()))
                .body(QuestWebMapper.toResponse(quest));
    }

    @GetMapping("/{id}")
    ResponseEntity<QuestResponse> findById(@PathVariable("id") String id) {
        return getQuest.get(new QuestId(id))
                .map(QuestWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/accept")
    public QuestResponse accept(@PathVariable("id") String id,
            @Valid @RequestBody AcceptQuestRequest request) {
        Quest quest = acceptQuest.accept(QuestWebMapper.toAcceptCommand(id, request));
        return QuestWebMapper.toResponse(quest);
    }

    @PostMapping("/{id}/complete")
    public QuestResponse complete(@PathVariable("id") String id) {
        Quest quest = completeQuest.complete(QuestWebMapper.toCompleteCommand(id));
        return QuestWebMapper.toResponse(quest);
    }

}
