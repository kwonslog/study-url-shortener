package com.example.shortener.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HighTrafficTestController {
    private final JdbcTemplate jdbcTemplate; // Hikari + JDBC 사용 가정

    @GetMapping("/healthz")
    public ResponseEntity<Void> ok() {
        return ResponseEntity.noContent().build(); // 204, 바디 없음
    }

    @GetMapping("/dbping")
    public ResponseEntity<String> ping() {
        Integer one = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        return ResponseEntity.ok(one != null ? "1" : "0");
    }
}
