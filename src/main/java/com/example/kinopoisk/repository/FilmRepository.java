package com.example.kinopoisk.repository;

import com.example.kinopoisk.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByFilmIdIn(Set<Long> filmIds);

    @Query("""
        SELECT f FROM Film f
        WHERE (:filmId IS NULL OR f.filmId = :filmId)
          AND (:filmName IS NULL OR LOWER(f.filmName) LIKE LOWER(CONCAT('%', CAST(:filmName AS String), '%')))
          AND (:yearFrom IS NULL OR f.year >= :yearFrom)
          AND (:yearTo IS NULL OR f.year <= :yearTo)
          AND (:ratingFrom IS NULL OR f.rating >= :ratingFrom)
          AND (:ratingTo IS NULL OR f.rating <= :ratingTo)
        """)
    Page<Film> search(
            @Param("filmId") Long filmId,
            @Param("filmName") String filmName,
            @Param("yearFrom") Integer yearFrom,
            @Param("yearTo") Integer yearTo,
            @Param("ratingFrom") BigDecimal ratingFrom,
            @Param("ratingTo") BigDecimal ratingTo,
            Pageable pageable);
}