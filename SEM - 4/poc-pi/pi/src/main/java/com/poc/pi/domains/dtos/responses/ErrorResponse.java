package com.poc.pi.domains.dtos.responses;

public record ErrorResponse(
        int status,
        String message,
        String path
) {
}
