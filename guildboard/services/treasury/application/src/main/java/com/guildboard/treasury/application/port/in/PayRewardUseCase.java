package com.guildboard.treasury.application.port.in;

import com.guildboard.treasury.domain.LedgerEntry;

/**
 * PayRewardUseCase
 */
public interface PayRewardUseCase {

    LedgerEntry pay(PayRewardCommand command);
}
