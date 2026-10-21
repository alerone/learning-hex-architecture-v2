package com.guildboard.quests.adapter.out.rest;

import java.net.http.HttpClient;
import java.time.Duration;

import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * AdventurerClientFactory
 */
public final class AdventurerClientFactory {

    private AdventurerClientFactory() {}

    public static RestClient create(String baseUrl, Duration connectTimeout, Duration readTimeout) {
        return create(RestClient.builder(), baseUrl, connectTimeout, readTimeout);
    }

    public static RestClient create(RestClient.Builder builder,
            String baseUrl, Duration connectTimeout, Duration readTimeout) {
        HttpClient http = HttpClient.newBuilder()
            .connectTimeout(connectTimeout)
            .build();
        JdkClientHttpRequestFactory factory = 
            new JdkClientHttpRequestFactory(http);
        factory.setReadTimeout(readTimeout);
        return builder
            .baseUrl(baseUrl)
            .requestFactory(factory)
            .requestInterceptor(new BearerTokenForwardingInterceptor())
            .build();
    }
    
}
