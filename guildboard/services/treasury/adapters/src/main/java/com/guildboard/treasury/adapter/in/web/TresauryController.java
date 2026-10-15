package com.guildboard.treasury.adapter.in.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guildboard.treasury.adapter.in.web.dto.AccountResponse;
import com.guildboard.treasury.adapter.in.web.dto.PayRewardRequest;
import com.guildboard.treasury.adapter.in.web.dto.PaymentResponse;
import com.guildboard.treasury.adapter.in.web.mapper.TresauryWebMapper;
import com.guildboard.treasury.application.port.in.GetAccountUseCase;
import com.guildboard.treasury.application.port.in.PayRewardUseCase;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.LedgerEntry;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TresauryController
 */
@RestController 
@RequiredArgsConstructor 
public class TresauryController {

    private final PayRewardUseCase payReward;
    private final GetAccountUseCase getAccount;


    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> pay(
           @Valid @RequestBody PayRewardRequest rq) {
           LedgerEntry entry = 
               payReward.pay(TresauryWebMapper.toCommand(rq));

           return ResponseEntity.status(HttpStatus.CREATED)
               .body(TresauryWebMapper.toResponse(entry));
    }

    @GetMapping("/accounts/{adventurerId}")
    public ResponseEntity<AccountResponse> account(
            @PathVariable("adventurerId") String adventurerId) {
            return getAccount.get(new AdventurerId(adventurerId))
                .map(TresauryWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
