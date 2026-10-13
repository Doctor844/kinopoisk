package com.example.kinopoisk.controller;


import com.example.kinopoisk.configs.EmailRequest;
import com.example.kinopoisk.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSimple(@RequestBody EmailRequest request) {
        emailService.sendSimple(request.to(), request.subject(), request.text());
        return ResponseEntity.ok("Email sent to " + request.to());
    }
}