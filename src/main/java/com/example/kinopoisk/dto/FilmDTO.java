package com.example.kinopoisk.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record FilmDTO(
        Long id,
        Long filmId,
        String filmName,
        Integer year,
        BigDecimal rating,
        String description)
        implements Serializable { }
