package com.example.kinopoisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KinopoiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(KinopoiskApplication.class, args);
    }

}
