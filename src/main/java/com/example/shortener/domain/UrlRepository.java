package com.example.shortener.domain;

import java.util.Optional;

public interface UrlRepository {
    Optional<UrlMapping> findByCode(String code);

    UrlMapping save(UrlMapping mapping);

    boolean exists(String code);
}