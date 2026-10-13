package com.example.kinopoisk.configs;

import com.example.kinopoisk.kafka.FilmProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic filmsTopic() {
        return TopicBuilder.name(FilmProducer.TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}