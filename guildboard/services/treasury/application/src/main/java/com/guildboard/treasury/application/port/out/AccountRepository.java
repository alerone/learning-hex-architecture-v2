package com.guildboard.treasury.application.port.out;

import java.util.Optional;

import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.AdventurerId;

/**
 * AccountRepository
 */
public interface AccountRepository {

    Optional<Account> findById(AdventurerId adventurerId);

    void save(Account account);
}
