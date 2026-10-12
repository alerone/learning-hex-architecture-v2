package com.guildboard.adapter.in.web.dto;

import com.guildboard.domain.Rank;

/**
 * AdventurerResponse
 */
public record AdventurerResponse(String id, String name, Rank rank, int experience) { }
