package com.example.kinopoisk.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "app.schedule")
public class GenreScheduleProperties {
    private Map<DayOfWeek, Integer> genres;
}