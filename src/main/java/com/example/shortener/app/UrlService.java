package com.example.shortener.app;

import com.example.shortener.domain.UrlMapping;
import com.example.shortener.domain.UrlRepository;
import com.example.shortener.support.NotFoundException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

@Service
public class UrlService {
    private final UrlRepository repo;
    private final CodeGenerator generator = new CodeGenerator();

    public UrlService(UrlRepository repo) {
        this.repo = repo;
    }

    public UrlMapping create(String originalUrl) {
        validateUrl(originalUrl);
        for (int i = 0; i < 5; i++) {
            String code = generator.generate(6);
            if (!repo.exists(code)) {
                UrlMapping saved = new UrlMapping(code, originalUrl, Instant.now());
                repo.save(saved);
                return saved;
            }
        }
        throw new IllegalStateException("failed to generate unique code");
    }

    public UrlMapping get(String code) {
        return repo.findByCode(code).orElseThrow(() -> new NotFoundException("code not found: " + code));
    }

    private void validateUrl(String url) {
        try {
            URI u = new URI(url);
            if (u.getScheme() == null || u.getHost() == null) {
                throw new IllegalArgumentException("invalid url");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("invalid url");
        }
    }
}