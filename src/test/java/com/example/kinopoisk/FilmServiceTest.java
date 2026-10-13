package com.example.kinopoisk;


import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmItem;
import com.example.kinopoisk.mapper.kinopoisk.KinopoiskFilmMapper;
import com.example.kinopoisk.model.Film;
import com.example.kinopoisk.repository.FilmRepository;
import com.example.kinopoisk.service.FilmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private KinopoiskFilmMapper kinopoiskFilmMapper;


    @InjectMocks
    private FilmService filmService;

    @Test
    void shouldSaveNewFilm() {

        KinopoiskFilmItem item = new KinopoiskFilmItem();
        item.setKinopoiskId(100L);
        item.setNameRu("Матрица");

        Film film = new Film();
        film.setFilmId(100L);
        film.setFilmName("Матрица");

        when(filmRepository.findAllByFilmIdIn(anySet()))
                .thenReturn(List.of());

        when(kinopoiskFilmMapper.toEntity(item))
                .thenReturn(film);

        when(filmRepository.saveAll(any()))
                .thenReturn(List.of(film));

        List<Film> result = filmService.saveNewFilms(List.of(item));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFilmName()).isEqualTo("Матрица");
    }


    @Test
    void shouldNotSaveExistingFilm() {
        KinopoiskFilmItem item = new KinopoiskFilmItem();
        item.setKinopoiskId(100L);

        Film existingFilm = new Film();
        existingFilm.setFilmId(100L);

        when(filmRepository.findAllByFilmIdIn(anySet()))
                .thenReturn(List.of(existingFilm));

        List<Film> result = filmService.saveNewFilms(List.of(item));

        assertThat(result).isEmpty();
        verify(filmRepository, never()).saveAll(any());
        verify(kinopoiskFilmMapper, never()).toEntity(any());
    }

    @Test
    void shouldReturnEmptyForEmptyInput() {
        List<Film> result = filmService.saveNewFilms(List.of());

        assertThat(result).isEmpty();
    }
}