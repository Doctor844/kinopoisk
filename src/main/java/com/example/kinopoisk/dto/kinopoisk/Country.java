package com.example.kinopoisk.dto.kinopoisk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private String country;
}