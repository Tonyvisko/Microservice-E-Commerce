package com.example.productservice.service;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse update(String id, ProductRequest request);

    ProductResponse getById(String id);

    List<ProductResponse> getAll();

    void delete(String id);

}
