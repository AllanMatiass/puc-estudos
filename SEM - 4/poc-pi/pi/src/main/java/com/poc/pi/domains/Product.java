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

    private int totalStock;

    private int purchased;

    @CreatedDate // Pega o instante que isso foi criado.
    private LocalDateTime createdAt;

}
