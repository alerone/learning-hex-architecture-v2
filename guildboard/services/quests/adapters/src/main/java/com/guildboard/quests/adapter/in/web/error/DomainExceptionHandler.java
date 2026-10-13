package com.guildboard.quests.adapter.in.web.error;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.DomainError;
import com.guildboard.quests.domain.DomainException;
import com.guildboard.quests.domain.QuestId;
import com.guildboard.quests.domain.QuestStatus;
import com.guildboard.quests.domain.Rank;

/**
 * DomainExceptionHandler
 */
@RestControllerAdvice 
public class DomainExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomain(DomainException exception){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
        statusFor(exception.getError()), exception.getMessage());

        problem.setTitle("Bussines rule violated");
        problem.setProperty("code", exception.getError().code());
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail argNotValid(MethodArgumentNotValidException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, 
                e.getMessage());

        List<String> errors = e.getBindingResult().getFieldErrors().stream().map(f -> 
                f.getField() + ": " + f.getDefaultMessage()).toList();

        problem.setProperty("errors", errors);
        return problem;
    }


    private HttpStatusCode statusFor(DomainError error) {
        return switch(error) {
            case DomainError.QuestNotFound(QuestId id) -> HttpStatusCode.valueOf(404);
            case DomainError.AdventurerNotFound(AdventurerId id) -> HttpStatusCode.valueOf(404);
            case DomainError.InvalidTransition(QuestId id, QuestStatus actual, QuestStatus expected) -> HttpStatusCode.valueOf(409);
            case DomainError.RankTooLow(Rank adventurerRank, Rank requiredRank) -> HttpStatusCode.valueOf(422);
            case DomainError.TooManyActiveQuests(int active, int limit) -> HttpStatusCode.valueOf(422);
            case DomainError.QuestNotPublished(QuestId id, QuestStatus actual) -> HttpStatusCode.valueOf(422);
            case DomainError.QuestExpired(QuestId id, Instant expiresAt) -> HttpStatusCode.valueOf(422);
        };
    }
    
}
