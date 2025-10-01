package com.example.shortener.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HighTrafficTestController {

    @GetMapping("/healthz")
    public ResponseEntity<Void> ok() {
        return ResponseEntity.noContent().build(); // 204, 바디 없음
    }
}
