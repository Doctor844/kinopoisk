package com.example.kinopoisk.service;

import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmItem;
import com.example.kinopoisk.mapper.kinopoisk.KinopoiskFilmMapper;
import com.example.kinopoisk.model.Film;
import com.example.kinopoisk.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final KinopoiskFilmMapper kinopoiskFilmMapper;

    @Transactional
    public List<Film> saveNewFilms(List<KinopoiskFilmItem> items) {
        Set<Long> incomingIds = items.stream()
                .map(KinopoiskFilmItem::getKinopoiskId)
                .collect(Collectors.toSet());

        Set<Long> existingIds = filmRepository.findAllByFilmIdIn(incomingIds).stream()
                .map(Film::getFilmId)
                .collect(Collectors.toSet());

        List<Film> toSave = items.stream()
                .filter(item -> !existingIds.contains(item.getKinopoiskId()))
                .map(kinopoiskFilmMapper::toEntity)
                .toList();

        return filmRepository.saveAll(toSave);
    }
}
