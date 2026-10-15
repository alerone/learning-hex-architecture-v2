package com.guildboard.treasury.adapter.in.web.error;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.guildboard.treasury.domain.DomainError;
import com.guildboard.treasury.domain.DomainException;
import com.guildboard.treasury.domain.QuestId;

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
            case DomainError.QuestAlreadyPaid(QuestId questId) -> HttpStatusCode.valueOf(409);
        };
    }
    
}
