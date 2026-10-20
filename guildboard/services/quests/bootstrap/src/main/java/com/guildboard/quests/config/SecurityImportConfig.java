package com.guildboard.quests.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.guildboard.quests.adapter.in.web.security.SecurityConfig;

/**
 * SecurityImportConfig
 */
@Configuration 
@Import(SecurityConfig.class)
public class SecurityImportConfig {
}
