package com.guildboard.treasury.application.port.in;

import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.Gold;

/**
 * GetLedgerBalanceUseCase
 */
public interface GetLedgerBalanceUseCase {

    Gold get(AdventurerId adventurerId);
}
