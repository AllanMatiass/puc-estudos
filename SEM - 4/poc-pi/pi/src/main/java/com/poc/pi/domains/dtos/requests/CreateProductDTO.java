package com.poc.pi.domains.dtos.requests;

import java.math.BigDecimal;

public record CreateProductDTO(
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
