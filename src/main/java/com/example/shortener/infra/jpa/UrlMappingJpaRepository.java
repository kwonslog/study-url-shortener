package com.example.shortener.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingJpaRepository extends JpaRepository<UrlMappingEntity, String> {
}