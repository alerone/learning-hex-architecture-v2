package com.guildboard.quests.adapter.in.web;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.guildboard.quests.adapter.in.web.dto.AcceptQuestRequest;
import com.guildboard.quests.adapter.in.web.dto.PublishQuestRequest;
import com.guildboard.quests.adapter.in.web.dto.QuestResponse;
import com.guildboard.quests.adapter.in.web.mapper.QuestWebMapper;
import com.guildboard.quests.application.port.in.AcceptQuestUseCase;
import com.guildboard.quests.application.port.in.CompleteQuestUseCase;
import com.guildboard.quests.application.port.in.GetQuestUseCase;
import com.guildboard.quests.application.port.in.PublishQuestCommand;
import com.guildboard.quests.application.port.in.PublishQuestUseCase;
import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;
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
    public QuestResponse accept(
            @PathVariable("id") String id,
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody(required = false) AcceptQuestRequest request) {
        String subject = jwt.getSubject();
        if (request != null && request.adventurerId() != null &&
                !request.adventurerId().equals(subject))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, 
                    "the token does not belong to that adventurer. You are trying to do malicious nasty things.");
        Quest quest = acceptQuest.accept(QuestWebMapper.toAcceptCommand(id, subject));
        return QuestWebMapper.toResponse(quest);
    }

    @PostMapping("/{id}/complete")
    public QuestResponse complete(@PathVariable("id") String id) {
        Quest quest = completeQuest.complete(QuestWebMapper.toCompleteCommand(id));
        return QuestWebMapper.toResponse(quest);
    }

}
