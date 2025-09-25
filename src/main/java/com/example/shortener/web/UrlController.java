package com.example.shortener.web;

import com.example.shortener.app.UrlService;
import com.example.shortener.domain.UrlMapping;
import com.example.shortener.dto.ShortenRequest;
import com.example.shortener.dto.ShortenResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {
    private final UrlService service;

    public UrlController(UrlService service) { this.service = service; }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("test");
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest req) {
        UrlMapping m = service.create(req.getUrl());
        String shortUrl = "http://localhost:8080/" + m.getCode();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ShortenResponse(m.getCode(), shortUrl));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        UrlMapping m = service.get(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, m.getOriginalUrl());
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302
    }
}
