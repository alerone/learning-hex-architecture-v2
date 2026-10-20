package com.guildboard.adventurers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.guildboard.adventurers.adapter.in.web.security.SecurityConfig;

/**
 * SecurityImportConfig
 */
@Configuration 
@Import(SecurityConfig.class)
public class SecurityImportConfig {
}
