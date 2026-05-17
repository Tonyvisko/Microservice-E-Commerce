package com.example.productservice.mapper;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest r) {
        if (r == null) return null;
        return Product.builder()
                .name(r.getName())
                .description(r.getDescription())
                .price(r.getPrice())
                .stockQuantity(r.getStockQuantity())
                .category(r.getCategory())
                .imageUrl(r.getImageUrl())
                .active(r.getActive() == null ? Boolean.TRUE : r.getActive())
                .build();
    }

    public ProductResponse toResponse(Product p) {
        if (p == null) return null;
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stockQuantity(p.getStockQuantity())
                .category(p.getCategory())
                .imageUrl(p.getImageUrl())
                .active(p.getActive())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
