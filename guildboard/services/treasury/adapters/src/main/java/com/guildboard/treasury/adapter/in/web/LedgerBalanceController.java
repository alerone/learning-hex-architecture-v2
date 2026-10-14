package com.guildboard.treasury.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.guildboard.treasury.adapter.in.web.dto.AccountResponse;
import com.guildboard.treasury.application.port.in.GetLedgerBalanceUseCase;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.Gold;

import lombok.RequiredArgsConstructor;

/**
 * LedgerBalanceController
 */
@RestController 
@RequiredArgsConstructor 
public class LedgerBalanceController {

    private final GetLedgerBalanceUseCase getBalance;

    @GetMapping("/accounts/{adventurerId}/ledger-balance")
    public AccountResponse balance(
            @PathVariable("adventurerId") String adventurerId)  {
            Gold balance = getBalance.get(new AdventurerId(adventurerId));

            return new AccountResponse(adventurerId, balance.coins());
    }
}
