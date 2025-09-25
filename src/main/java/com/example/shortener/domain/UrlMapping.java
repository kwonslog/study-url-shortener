package com.example.shortener.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
public class UrlMapping {
    @Getter
    private final String code;         // 단축 코드 (Base62)
    @Getter
    private final String originalUrl;  // 원본 URL
    @Getter
    private final Instant createdAt;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlMapping)) return false;
        UrlMapping that = (UrlMapping) o;
        return Objects.equals(code, that.code);
    }

    @Override public int hashCode() { return Objects.hash(code); }
}
