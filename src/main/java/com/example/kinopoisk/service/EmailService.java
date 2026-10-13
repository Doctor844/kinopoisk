package com.example.kinopoisk.service;

import com.example.kinopoisk.dto.kinopoisk.KinopoiskFilmItem;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper;

    @Value("${app.mail.to}")
    private String to;

    @Value("${spring.mail.username}")
    private String from;


    public void sendSimple(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        log.info("Email sent to {}", to);
    }


    public void sendFilmsJson(List<KinopoiskFilmItem> films) {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(films);

            String filename = "films-" + LocalDate.now() + ".json";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Фильмы за " + LocalDate.now());
            helper.setText("Отчёт во вложении: " + filename);

            helper.addAttachment(
                    filename,
                    new ByteArrayResource(json.getBytes(StandardCharsets.UTF_8))
            );

            mailSender.send(message);
            log.info("Email with attachment sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email", e);
            throw new RuntimeException("Email sending failed", e);
        }
    }
}