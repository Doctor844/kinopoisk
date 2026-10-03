package com.example.kinopoisk.controller;

import com.example.kinopoisk.dto.FilmDTO;
import com.example.kinopoisk.mapper.FilmMapper;
import com.example.kinopoisk.model.Film;
import com.example.kinopoisk.repository.FilmRepository;
import com.example.kinopoisk.service.FilmService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Validated
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Page<FilmDTO> search(
            @RequestParam(required = false) Long filmId,
            @RequestParam(required = false) String filmName,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(required = false) BigDecimal ratingFrom,
            @RequestParam(required = false) BigDecimal ratingTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return filmService.search(filmId, filmName, yearFrom, yearTo,
                ratingFrom, ratingTo, pageable);
    }
}