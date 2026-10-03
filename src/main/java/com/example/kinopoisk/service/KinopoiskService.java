package com.example.kinopoisk.service;

import com.example.kinopoisk.dto.FilmDTO;
import com.example.kinopoisk.dto.FilmSearchParams;
import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmsResponse;
import com.example.kinopoisk.mapper.FilmMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import static java.util.Optional.ofNullable;

@Service
public class KinopoiskService {
    private final String url;
    private final String endpoint;
    private final RestTemplate restTemplate;
    private final FilmService filmService;
    private final FilmMapper filmMapper;

    public KinopoiskService(
            @Qualifier("kinopoiskRestTemplate") RestTemplate restTemplate,
            @Value("${kinopoisk.api.url}") String url,
            @Value("${kinopoisk.api.v2.films}") String endpoint,
            FilmService filmService,
            FilmMapper filmMapper) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.endpoint = endpoint;
        this.filmService = filmService;
        this.filmMapper = filmMapper;
    }

    public KinopoiskFilmsResponse searchFilms(FilmSearchParams params) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url + endpoint)
                .queryParamIfPresent("countries", ofNullable(params.countries()))
                .queryParamIfPresent("genres", ofNullable(params.genres()))
                .queryParamIfPresent("order", ofNullable(params.order()).map(Enum::name))
                .queryParamIfPresent("type", ofNullable(params.type()).map(Enum::name))
                .queryParamIfPresent("ratingFrom", ofNullable(params.ratingFrom()))
                .queryParamIfPresent("ratingTo", ofNullable(params.ratingTo()))
                .queryParamIfPresent("yearFrom", ofNullable(params.yearFrom()))
                .queryParamIfPresent("yearTo", ofNullable(params.yearTo()))
                .queryParamIfPresent("imdbId", ofNullable(params.imdbId()))
                .queryParamIfPresent("keyword", ofNullable(params.keyword()))
                .queryParamIfPresent("page", ofNullable(params.page()));

        URI uri = builder.build().encode().toUri();
        return restTemplate.getForObject(uri, KinopoiskFilmsResponse.class);

    }

    public List<FilmDTO> searchAndSave(FilmSearchParams params) {
        KinopoiskFilmsResponse response = searchFilms(params);

        if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
            return List.of();
        }

        return filmService.saveNewFilms(response.getItems()).stream()
                .map(filmMapper::toDto)
                .toList();
    }




}
