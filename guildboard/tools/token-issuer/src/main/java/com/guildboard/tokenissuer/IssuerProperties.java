package com.guildboard.tokenissuer;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * IssuerProperties
 */
@ConfigurationProperties("guildboard.token-issuer")
public record IssuerProperties(
        String issuer,
        @DefaultValue("15m") Duration ttl,
        List<User> users) {

    public record User(String username, String password, String subject, String role);
}
