package com.example.shortener.infra;

import com.example.shortener.domain.UrlMapping;
import com.example.shortener.domain.UrlRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUrlRepository implements UrlRepository {
    private final Map<String, UrlMapping> byCode = new ConcurrentHashMap<>();

    @Override
    public Optional<UrlMapping> findByCode(String code) {
        return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public UrlMapping save(UrlMapping m) {
        byCode.put(m.getCode(), m);
        return m;
    }

    @Override
    public boolean exists(String code) {
        return byCode.containsKey(code);
    }
}