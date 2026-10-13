package com.example.kinopoisk.kafka;

import com.example.kinopoisk.service.EmailService;
import com.example.kinopoisk.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmConsumer {

    private final FilmService filmService;
    private final EmailService emailService;

    @KafkaListener(topics = FilmProducer.TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consume(FilmBatchMessage message, Acknowledgment ack) {
        try {
            log.info("Received batch of {} films", message.films().size());
            filmService.saveNewFilms(message.films());
            emailService.sendFilmsJson(message.films());
            ack.acknowledge();
            log.info("Batch processed, offset committed");
        } catch (Exception e) {
            log.error("Failed, offset NOT committed", e);
            throw e;
        }
    }
}