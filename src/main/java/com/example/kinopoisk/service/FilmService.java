package com.example.kinopoisk.service;

import com.example.kinopoisk.dto.FilmDTO;
import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmItem;
import com.example.kinopoisk.mapper.FilmMapper;
import com.example.kinopoisk.mapper.kinopoisk.KinopoiskFilmMapper;
import com.example.kinopoisk.model.Film;
import com.example.kinopoisk.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final KinopoiskFilmMapper kinopoiskFilmMapper;
    private final FilmMapper filmMapper;
    @Transactional
    public List<Film> saveNewFilms(List<KinopoiskFilmItem> items) {

        Map<Long, KinopoiskFilmItem> uniqueItems = items.stream()
                .filter(item -> item.getKinopoiskId() != null)
                .collect(Collectors.toMap(
                        KinopoiskFilmItem::getKinopoiskId,
                        item -> item,
                        (existing, replacement) -> existing
                ));

        if (uniqueItems.isEmpty()) {
            return List.of();
        }

        Set<Long> existingIds = filmRepository
                .findAllByFilmIdIn(uniqueItems.keySet()).stream()
                .map(Film::getFilmId)
                .collect(Collectors.toSet());

        List<Film> toSave = uniqueItems.values().stream()
                .filter(item -> !existingIds.contains(item.getKinopoiskId()))
                .map(kinopoiskFilmMapper::toEntity)
                .toList();

        if (toSave.isEmpty()) {
            return List.of();
        }

        return filmRepository.saveAll(toSave);
    }

    public Page<FilmDTO> search(Long filmId, String filmName,
                                Integer yearFrom, Integer yearTo,
                                BigDecimal ratingFrom, BigDecimal ratingTo,
                                Pageable pageable) {
        return filmRepository.search(filmId, filmName, yearFrom, yearTo,
                        ratingFrom, ratingTo, pageable)
                .map(filmMapper::toDto);
    }
}
