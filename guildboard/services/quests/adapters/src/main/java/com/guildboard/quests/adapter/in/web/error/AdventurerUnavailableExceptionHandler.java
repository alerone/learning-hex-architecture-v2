package com.guildboard.quests.adapter.in.web.error;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.guildboard.quests.application.port.out.AdventurerUnavailableException;

import lombok.extern.slf4j.Slf4j;

/**
 * AdventurerUnavailableExceptionHandler
 */
@Slf4j 
@RestControllerAdvice 
public class AdventurerUnavailableExceptionHandler {

    
    @ExceptionHandler(AdventurerUnavailableException.class)
    public ProblemDetail handleUnavailable(
            AdventurerUnavailableException e) {
        log.warn("adventurers unavailable: {} ({})", e.getMessage(), e.getCause());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, 
                "the adventurers service cannot answer right now");
        problem.setProperty("code", "ADVENTURERS_UNAVAILABLE");
        return problem;
    }


    
}
