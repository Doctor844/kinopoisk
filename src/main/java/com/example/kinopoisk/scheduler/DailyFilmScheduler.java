package com.example.kinopoisk.scheduler;

import com.example.kinopoisk.configs.GenreScheduleProperties;
import com.example.kinopoisk.dto.FilmSearchParams;
import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmItem;
import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmsResponse;
import com.example.kinopoisk.kafka.FilmBatchMessage;
import com.example.kinopoisk.kafka.FilmProducer;
import com.example.kinopoisk.service.KinopoiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyFilmScheduler {

    private final KinopoiskService kinopoiskService;
    private final FilmProducer filmProducer;
    private final GenreScheduleProperties genreProperties;


    @Scheduled(cron = "${app.schedule.cron}", zone = "Europe/Moscow")
    public void fetchDailyFilms() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        fetchFilmsForDay(today);
    }

    public void fetchFilmsForDay(DayOfWeek day) {
        Integer genreId = genreProperties.getGenres().get(day);
        if (genreId == null) {
            log.warn("No genre configured for {}", day);
            return;
        }

        Map<Long, KinopoiskFilmItem> uniqueFilms = new LinkedHashMap<>();
        int page = 1;

        while (uniqueFilms.size() < 50 && page <= 3) {
            FilmSearchParams params = new FilmSearchParams(
                    null, genreId, null, null,
                    null, null, null, null,
                    null, null, page
            );

            KinopoiskFilmsResponse response = kinopoiskService.searchFilms(params);
            if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
                break;
            }

            for (KinopoiskFilmItem item : response.getItems()) {
                if (item.getKinopoiskId() == null) continue;
                uniqueFilms.putIfAbsent(item.getKinopoiskId(), item);
                if (uniqueFilms.size() >= 50) break;
            }
            page++;
        }

        if (uniqueFilms.isEmpty()) {
            log.warn("No films fetched for {}", day);
            return;
        }

        List<KinopoiskFilmItem> collected = new ArrayList<>(uniqueFilms.values());
        log.info("Fetched {} unique films for {}", collected.size(), day);
        filmProducer.sendBatch(new FilmBatchMessage(collected));
    }
}