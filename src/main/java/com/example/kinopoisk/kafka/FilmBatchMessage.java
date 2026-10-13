package com.example.kinopoisk.kafka;

import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmItem;

import java.io.Serializable;
import java.util.List;

public record FilmBatchMessage(List<KinopoiskFilmItem> films) implements Serializable { }