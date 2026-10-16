package com.guildboard.treasury.adapter.in.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.guildboard.treasury.adapter.in.web.dto.AccountResponse;
import com.guildboard.treasury.adapter.in.web.mapper.TresauryWebMapper;
import com.guildboard.treasury.application.port.in.GetAccountUseCase;
import com.guildboard.treasury.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * TresauryController
 */
@RestController 
@RequiredArgsConstructor 
public class TresauryController {

    private final GetAccountUseCase getAccount;


    @GetMapping("/accounts/{adventurerId}")
    public ResponseEntity<AccountResponse> account(
            @PathVariable("adventurerId") String adventurerId) {
            return getAccount.get(new AdventurerId(adventurerId))
                .map(TresauryWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
