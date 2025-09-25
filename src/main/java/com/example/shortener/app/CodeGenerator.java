package com.example.shortener.app;

import java.security.SecureRandom;

public class CodeGenerator {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RND = new SecureRandom();

    public String generate(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(BASE62.charAt(RND.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
}
