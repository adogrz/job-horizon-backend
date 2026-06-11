package com.jobhorizon.backend.config;

import java.time.LocalDateTime;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data,
    LocalDateTime timestamp
) {
    public ApiResponse(boolean success, String message, T data) {
        this(success, message, data, LocalDateTime.now());
    }

    public ApiResponse(boolean success, String message) {
        this(success, message, null, LocalDateTime.now());
    }
}
