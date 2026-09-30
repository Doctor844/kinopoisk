package com.example.kinopoisk.dto;

import com.example.kinopoisk.enums.FilmOrder;
import com.example.kinopoisk.enums.FilmType;

public record FilmSearchParams(
        Integer countries,
        Integer genres,
        FilmOrder order,
        FilmType type,
        Integer ratingFrom,
        Integer ratingTo,
        Integer yearFrom,
        Integer yearTo,
        String imdbId,
        String keyword,
        Integer page) {

}