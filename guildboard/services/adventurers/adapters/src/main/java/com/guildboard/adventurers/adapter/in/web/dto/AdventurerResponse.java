package com.guildboard.adventurers.adapter.in.web.dto;

import com.guildboard.adventurers.domain.Rank;

/**
 * AdventurerResponse
 */
public record AdventurerResponse(String id, String name, Rank rank, int experience) { }
