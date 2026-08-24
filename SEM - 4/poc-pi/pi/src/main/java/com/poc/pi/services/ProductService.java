package com.poc.pi.services;

import com.poc.pi.domains.Product;
import com.poc.pi.domains.dtos.requests.CreateProductDTO;
import com.poc.pi.repositories.ProductRepository;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(CreateProductDTO dto){
        // TODO: Fazer a lógica aqui
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
