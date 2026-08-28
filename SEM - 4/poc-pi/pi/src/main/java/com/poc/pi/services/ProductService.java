package com.poc.pi.services;

import com.poc.pi.domains.Product;
import com.poc.pi.domains.dtos.requests.CreateProductDTO;
import com.poc.pi.domains.dtos.responses.ProductResponseDTO;
import com.poc.pi.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductResponseDTO create(CreateProductDTO dto){
        if (dto.name() == null || dto.description() == null ||
                dto.name().isBlank() || dto.description().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome e Descrição não podem ser vazias");
        }

        if (dto.price().compareTo(BigDecimal.ZERO) <= 0 || dto.quantity() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preço e Quantidade devem ser maiores que 0");
        }

        Product product = repository.save(dto.mapToEntity());

        return new ProductResponseDTO(product);
    }

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }
}
