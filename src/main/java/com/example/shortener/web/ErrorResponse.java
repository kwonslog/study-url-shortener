package com.example.shortener.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorResponse {
    @Getter
    private final String code;   // 애플리케이션 에러 코드
    @Getter
    private final String message;
}
