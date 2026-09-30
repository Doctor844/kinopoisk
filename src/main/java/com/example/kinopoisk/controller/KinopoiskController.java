package com.example.kinopoisk.controller;

import com.example.kinopoisk.dto.FilmDTO;
import com.example.kinopoisk.dto.FilmSearchParams;
import com.example.kinopoisk.mapper.FilmMapper;
import com.example.kinopoisk.service.KinopoiskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class KinopoiskController {

    private final KinopoiskService kinopoiskService;
    private final FilmMapper filmMapper;

    public KinopoiskController(KinopoiskService kinopoiskService, FilmMapper filmMapper) {
        this.kinopoiskService = kinopoiskService;
        this.filmMapper = filmMapper;
    }

    @GetMapping("/films")
    public List<FilmDTO> importFilms(FilmSearchParams params) {
        return kinopoiskService.searchAndSave(params).stream()
                .map(filmMapper::toDto)
                .toList();
    }
}
