package com.example.kinopoisk.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final String kinopoiskApiKey;

    public RestTemplateConfig(@Value("${kinopoisk.api.key}") String kinopoiskApiKey) {
        this.kinopoiskApiKey = kinopoiskApiKey;
    }
    @Bean
    public RestTemplate kinopoiskRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);

        RestTemplate restTemplate = new RestTemplate(factory);

        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-API-KEY", kinopoiskApiKey);
            request.getHeaders().add("Accept", "application/json");
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
