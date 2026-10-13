package com.example.kinopoisk.configs;

public record EmailRequest(
        String to,
        String subject,
        String text) {
}
