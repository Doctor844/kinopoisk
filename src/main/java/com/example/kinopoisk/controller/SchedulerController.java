package com.example.kinopoisk.controller;

import com.example.kinopoisk.scheduler.DailyFilmScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;

@RestController
@RequestMapping("/admin/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final DailyFilmScheduler scheduler;

    @PostMapping("/trigger")
    public String trigger() {
        scheduler.fetchFilmsForDay(java.time.LocalDate.now().getDayOfWeek());
        return "Triggered";
    }

    @PostMapping("/trigger/{day}")
    public String triggerForDay(@PathVariable DayOfWeek day) {
        scheduler.fetchFilmsForDay(day);
        return "Triggered for " + day;
    }
}