package com.guildboard.quests.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import com.guildboard.quests.adapter.out.messaging.KafkaQuestEventPublisher;
import com.guildboard.quests.adapter.out.messaging.QuestCompleted;
import com.guildboard.quests.application.port.out.QuestEventPublisher;

/**
 * MessagingConfig
 */
@Configuration 
public class MessagingConfig {

    @Bean 
    public NewTopic questCompletedTopic(
            @Value("${guildboard.messaging.topic}") String topic) {
            return TopicBuilder.name(topic).partitions(1).replicas(1).build();
    }

    @Bean 
    public QuestEventPublisher questEventPublisher(
            KafkaTemplate<String, QuestCompleted> kafkaTemplate,
            @Value("${guildboard.messaging.topic}") String topic) {
        return new KafkaQuestEventPublisher(kafkaTemplate, topic);
    }
}
