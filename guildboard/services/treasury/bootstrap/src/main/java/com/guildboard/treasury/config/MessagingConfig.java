package com.guildboard.treasury.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

/**
 * MessagingConfig
 */
@Configuration 
public class MessagingConfig {

    @Bean 
    public NewTopic deadLetterTopic(
            @Value("${guildboard.messaging.dead-letter-topic}") String topic) {
        return TopicBuilder.name(topic).partitions(1).replicas(1).build();
    }

    @Bean 
    public CommonErrorHandler errorHandler(
            ProducerFactory<Object, Object> producerFactory,
            @Value("${guildboard.messaging.dead-letter-topic}") String topic) {
        KafkaTemplate<Object, Object> bytes = new KafkaTemplate<>(
                producerFactory.copyWithConfigurationOverride(
                    Map.<String,Object>of(
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        ByteArraySerializer.class)));

        KafkaTemplate<Object, Object> json = new KafkaTemplate<>(
                producerFactory.copyWithConfigurationOverride(
                    Map.<String, Object>of(
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        JacksonJsonSerializer.class,
                        "spring.json.add.type.headers", false)));

        Map<Class<?>, KafkaOperations<?, ?>> templates = 
            new LinkedHashMap<>();
        templates.put(byte[].class, bytes);
        templates.put(Object.class, json);

        DeadLetterPublishingRecoverer recoverer = 
            new DeadLetterPublishingRecoverer(templates, (failed, exception) ->
                        new TopicPartition(topic, failed.partition()));

        ExponentialBackOffWithMaxRetries backOff = 
            new ExponentialBackOffWithMaxRetries(3);
        backOff.setInitialInterval(1_000L);
        backOff.setMultiplier(2.0);
        backOff.setMaxInterval(10_000L);
        return new DefaultErrorHandler(recoverer, backOff);
    }


    
}
