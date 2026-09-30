package com.example.kinopoisk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_seq")
    @SequenceGenerator(name = "film_seq", sequenceName = "films_id_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "film_id", unique = true)
    private Long filmId;

    @Column(name = "film_name", columnDefinition = "TEXT")
    private String filmName;

    @Column(name = "year")
    private Integer year;

    @Column(name = "rating", precision = 4, scale = 2)
    private BigDecimal rating;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
