package com.example.shortener.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShortenResponse {
    @Getter
    private final String code;
    @Getter
    private final String shortUrl;
}
