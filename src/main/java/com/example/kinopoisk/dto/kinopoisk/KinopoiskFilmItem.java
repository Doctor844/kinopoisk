package com.example.kinopoisk.dto.kinopoisk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KinopoiskFilmItem {
    private Long kinopoiskId;
    private String imdbId;
    private String nameRu;
    private String nameEn;
    private String nameOriginal;
    private List<Country> countries;
    private List<Genre> genres;
    private BigDecimal ratingKinopoisk;
    private BigDecimal ratingImdb;
    private Integer year;
    private String type;
    private String posterUrl;
    private String posterUrlPreview;
}
