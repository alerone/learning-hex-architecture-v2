package com.guildboard.treasury.domain;

import java.util.List;

/**
 * Ledger
 */
public final class Ledger {

    private Ledger() {}

    public static Gold balanceOf(List<LedgerEntry> entries) {
        return entries.stream()
            .map(LedgerEntry::amount)
            .reduce(Gold::plus)
            .orElseGet(() -> Gold.ZERO);
    }

    
}
