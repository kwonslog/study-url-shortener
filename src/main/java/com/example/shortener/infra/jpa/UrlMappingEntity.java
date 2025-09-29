package com.example.shortener.infra.jpa;

import com.example.shortener.domain.UrlMapping;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "url_mappings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlMappingEntity {
    @Id
    @Column(name = "code", nullable = false, length = 16)
    private String code;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public UrlMappingEntity(String code, String originalUrl, Instant createdAt) {
        this.code = code;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
    }

    public static UrlMappingEntity fromDomain(UrlMapping mapping) {
        return new UrlMappingEntity(mapping.getCode(), mapping.getOriginalUrl(), mapping.getCreatedAt());
    }

    public UrlMapping toDomain() {
        return new UrlMapping(code, originalUrl, createdAt);
    }
}