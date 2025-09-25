package com.example.shortener.infra;

import com.example.shortener.domain.UrlMapping;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUrlRepository {
    private final Map<String, UrlMapping> byCode = new ConcurrentHashMap<>();

    public Optional<UrlMapping> findByCode(String code) {
        return Optional.ofNullable(byCode.get(code));
    }

    public UrlMapping save(UrlMapping m) {
        byCode.put(m.getCode(), m);
        return m;
    }

    public boolean exists(String code) {
        return byCode.containsKey(code);
    }
}
