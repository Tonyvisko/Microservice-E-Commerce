package com.example.productservice.impl;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public ProductResponse create(ProductRequest request) {
        Product p = mapper.toEntity(request);
        Instant now = Instant.now();
        p.setCreatedAt(now);
        p.setUpdatedAt(now);
        Product saved = repository.save(p);
        return mapper.toResponse(saved);
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStockQuantity(request.getStockQuantity());
        existing.setCategory(request.getCategory());
        existing.setImageUrl(request.getImageUrl());
        existing.setActive(request.getActive());
        existing.setUpdatedAt(Instant.now());
        Product saved = repository.save(existing);
        return mapper.toResponse(saved);
    }

    @Override
    public ProductResponse getById(String id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Override
    public List<ProductResponse> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        repository.deleteById(id);
    }
}
