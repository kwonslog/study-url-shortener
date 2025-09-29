package com.example.shortener.infra.jpa;

import com.example.shortener.domain.UrlMapping;
import com.example.shortener.domain.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository @Primary
@RequiredArgsConstructor
public class JpaUrlRepository implements UrlRepository {
    private final UrlMappingJpaRepository repository;

    @Override
    public Optional<UrlMapping> findByCode(String code) {
        return repository.findById(code).map(UrlMappingEntity::toDomain);
    }

    @Override
    public UrlMapping save(UrlMapping mapping) {
        UrlMappingEntity entity = UrlMappingEntity.fromDomain(mapping);
        return repository.save(entity).toDomain();
    }

    @Override
    public boolean exists(String code) {
        return repository.existsById(code);
    }
}