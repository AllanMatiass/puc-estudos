package com.poc.pi.domains.dtos.requests;

import com.poc.pi.domains.Product;

import java.math.BigDecimal;

public record CreateProductDTO(
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
    public Product mapToEntity(){
        return new Product(this.name, this.description, this.price, this.quantity);
    }
}
