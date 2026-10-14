package com.guildboard.treasury.application.port.in;

import java.util.Optional;

import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.AdventurerId;

/**
 * GetAccountUseCase
 */
public interface GetAccountUseCase {

    Optional<Account> get(AdventurerId adventurerId);
}
