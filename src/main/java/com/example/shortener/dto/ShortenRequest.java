package com.example.shortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class ShortenRequest {
    @NotBlank
    @Getter @Setter
    private String url;
}
