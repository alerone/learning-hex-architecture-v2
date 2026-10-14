package com.guildboard.treasury.application.service;

import java.util.Optional;

import com.guildboard.treasury.application.port.in.GetAccountUseCase;
import com.guildboard.treasury.application.port.out.AccountRepository;
import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * GetAccountService
 */
@RequiredArgsConstructor 
public class GetAccountService implements GetAccountUseCase{

    private final AccountRepository accounts;

    @Override
    public Optional<Account> get(AdventurerId adventurerId) {
        return accounts.findById(adventurerId);
    }

    
}
