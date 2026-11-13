package com.example.kinopoisk;

import com.example.kinopoisk.controller.FilmController;
import com.example.kinopoisk.dto.FilmDTO;
import com.example.kinopoisk.service.FilmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // В Spring Boot 4 @MockBean устарел, используем @MockitoBean
    @MockitoBean
    private FilmService filmService;

    @Test
    @DisplayName("GET /films: возвращает 200 OK и страницу с фильмами")
    void shouldReturnFilmsPage() throws Exception {
        // GIVEN
        FilmDTO filmDto = new FilmDTO(1L, 100L, "Матрица", 1999, BigDecimal.valueOf(9.0), "Описание");
        Page<FilmDTO> page = new PageImpl<>(List.of(filmDto));

        when(filmService.search(any(), any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(page);

        // WHEN & THEN
        mockMvc.perform(get("/films")
                        .param("filmName", "Матрица")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].filmName").value("Матрица"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("GET /films: возвращает 400 Bad Request, если size больше 100")
    void shouldReturnBadRequestForInvalidSize() throws Exception {
        mockMvc.perform(get("/films")
                        .param("size", "1000") // Нарушает @Max(100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}