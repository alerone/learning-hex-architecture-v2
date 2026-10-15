package com.guildboard.treasury.adapter.out.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


import com.guildboard.treasury.application.port.out.AccountRepository;
import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.AdventurerId;

/**
 * InMemoryAccountRepository
 */
public class InMemoryAccountRepository implements AccountRepository{
    private final Map<AdventurerId, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Optional<Account> findById(AdventurerId adventurerId) {
        System.out.print(accounts.get(adventurerId));
        return Optional.ofNullable(accounts.get(adventurerId));
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getOwner(), account);
    }
}
