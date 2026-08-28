package com.poc.pi.domains;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class Product {

    @Id
    private UUID id;

    private String name;

    private String description;

    // Por que BigDecimal? É mais preciso que os tipos primitivos como Float/Double por exemplo
    private BigDecimal price;

    private int quantity;

    @CreatedDate
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateText(name, "name");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateText(description, "description");
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {

        // basicamente diz: se o preço passado comparado a zero for negativo, lança erro (pois é negativo)
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        // Uma alternativa seria:
//        if (price.doubleValue() <= 0){
//            throw new IllegalArgumentException("Price must be a positive number");
//        }
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Total stock cannot be negative");
        }

        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Product(UUID id){
        this.id = id;
    }

    public Product(
            String name,
            String description,
            BigDecimal price,
            int quantity
    ) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setQuantity(quantity);
    }

    private void validateText(String text, String fieldName){
        if (text == null || text.isBlank()) {
            // vamos tratar isso depois
            throw new IllegalArgumentException(String.format("%s cannot be null or empty", fieldName));
        }
    }
}