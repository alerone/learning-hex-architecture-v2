package com.guildboard.treasury.adapter.in.web.dto;

/**
 * PaymentResponse
 */
public record PaymentResponse(
        String id,
        String questId,
        String adventurerId,
        int amount,
        String createdAt) {
}
