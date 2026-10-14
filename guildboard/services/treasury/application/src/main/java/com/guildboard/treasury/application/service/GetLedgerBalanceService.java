package com.guildboard.treasury.application.service;

import com.guildboard.treasury.application.port.in.GetLedgerBalanceUseCase;
import com.guildboard.treasury.application.port.out.LedgerRepository;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.Gold;
import com.guildboard.treasury.domain.Ledger;

import lombok.RequiredArgsConstructor;

/**
 * GetLedgerBalanceService
 */
@RequiredArgsConstructor 
public class GetLedgerBalanceService implements GetLedgerBalanceUseCase{

    private final LedgerRepository ledgers;

    @Override
    public Gold get(AdventurerId adventurerId) {
        return Ledger.balanceOf(ledgers.findByAdventurerId(adventurerId));
    }

    
}
