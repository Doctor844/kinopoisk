package com.example.kinopoisk;


import com.example.kinopoisk.model.Film;
import com.example.kinopoisk.repository.FilmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FilmRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FilmRepository filmRepository;

    @Test
    @DisplayName("search: поиск по подстроке в названии, регистронезависимый")
    void shouldSearchByNameIgnoringCase() {
        saveFilm(100L, "Матрица", 1999, 9.0);
        saveFilm(200L, "Матрица 2", 2003, 8.5);
        saveFilm(300L, "Терминатор", 1984, 8.0);

        Pageable pageable = PageRequest.of(0, 20);

        Page<Film> result = filmRepository.search(
                null, "матрица", null, null, null, null, pageable
        );

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(Film::getFilmName)
                .containsExactlyInAnyOrder("Матрица", "Матрица 2");
    }

    private void saveFilm(Long filmId, String name, int year, double rating) {
        Film film = new Film();
        film.setFilmId(filmId);
        film.setFilmName(name);
        film.setYear(year);
        film.setRating(BigDecimal.valueOf(rating));
        entityManager.persist(film);
        entityManager.flush();
    }
}