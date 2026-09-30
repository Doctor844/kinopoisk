package com.example.kinopoisk.mapper.kinopoisk;

import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmItem;
import com.example.kinopoisk.model.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KinopoiskFilmMapper {

    @Mapping(source = "kinopoiskId", target = "filmId")
    @Mapping(target = "filmName", expression = "java(resolveName(item))")
    @Mapping(source = "ratingKinopoisk", target = "rating")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "year", target = "year")
    @Mapping(target = "description", ignore = true)
    Film toEntity(KinopoiskFilmItem item);

    default String resolveName(KinopoiskFilmItem item) {
        if (item.getNameRu() != null && !item.getNameRu().isBlank()) return item.getNameRu();
        if (item.getNameOriginal() != null && !item.getNameOriginal().isBlank()) return item.getNameOriginal();
        if (item.getNameEn() != null && !item.getNameEn().isBlank()) return item.getNameEn();
        return "Без названия";
    }
}