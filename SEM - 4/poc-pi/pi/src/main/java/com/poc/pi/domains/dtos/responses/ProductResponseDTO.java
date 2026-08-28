package com.poc.pi.domains.dtos.responses;

import com.poc.pi.domains.Product;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        double price,
        int quantity,
        LocalDateTime createdAt
) {
    public ProductResponseDTO(Product p){
        this(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice().doubleValue(),
                p.getQuantity(),
                p.getCreatedAt()
        );
    }
}
