package com.example.kinopoisk.dto.kinopoisk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"total", "totalPages", "items"})
public class KinopoiskFilmsResponse {
    private Integer total;
    private Integer totalPages;
    private List<KinopoiskFilmItem> items;
}