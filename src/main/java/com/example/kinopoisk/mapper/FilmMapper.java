package com.example.kinopoisk.mapper;

import com.example.kinopoisk.dto.FilmDTO;
import com.example.kinopoisk.model.Film;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FilmMapper {


    FilmDTO toDto(Film film);

}
