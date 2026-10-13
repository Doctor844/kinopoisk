package com.example.kinopoisk.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class FilmProducer {

    public static final String TOPIC = "films";

    private final KafkaTemplate<String, FilmBatchMessage> kafkaTemplate;

    public void sendBatch(FilmBatchMessage message) {
        kafkaTemplate.send(TOPIC, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send batch", ex);
                    } else {
                        log.info("Sent batch of {} films, offset={}",
                                message.films().size(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}