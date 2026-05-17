package com.example.productservice.service;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.impl.ProductServiceImpl;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void create_shouldSaveAndReturnResponse() {
        ProductRequest req = ProductRequest.builder()
                .name("Product A")
                .price(new BigDecimal("9.99"))
                .stockQuantity(10)
                .active(true)
                .build();

        Product entity = Product.builder().name("Product A").price(req.getPrice()).stockQuantity(10).active(true).build();
        Product saved = Product.builder().id("1").name("Product A").price(req.getPrice()).stockQuantity(10).active(true).createdAt(Instant.now()).updatedAt(Instant.now()).build();
        ProductResponse resp = ProductResponse.builder().id("1").name("Product A").price(req.getPrice()).stockQuantity(10).active(true).build();

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(resp);

        ProductResponse result = service.create(req);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void getById_existing_returnsResponse() {
        Product saved = Product.builder().id("1").name("Product A").build();
        ProductResponse resp = ProductResponse.builder().id("1").name("Product A").build();
        when(repository.findById("1")).thenReturn(Optional.of(saved));
        when(mapper.toResponse(saved)).thenReturn(resp);

        ProductResponse result = service.getById("1");
        assertEquals("1", result.getId());
    }

    @Test
    void getById_missing_throws() {
        when(repository.findById("x")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById("x"));
    }

    @Test
    void getAll_returnsList() {
        Product p = Product.builder().id("1").name("P").build();
        ProductResponse r = ProductResponse.builder().id("1").name("P").build();
        when(repository.findAll()).thenReturn(List.of(p));
        when(mapper.toResponse(p)).thenReturn(r);

        List<ProductResponse> list = service.getAll();
        assertEquals(1, list.size());
    }

    @Test
    void update_existing_updates() {
        Product existing = Product.builder().id("1").name("Old").price(new BigDecimal("1")).stockQuantity(1).build();
        ProductRequest req = ProductRequest.builder().name("New").price(new BigDecimal("2")).stockQuantity(5).active(true).build();
        Product saved = Product.builder().id("1").name("New").price(req.getPrice()).stockQuantity(req.getStockQuantity()).active(true).build();
        ProductResponse resp = ProductResponse.builder().id("1").name("New").price(req.getPrice()).stockQuantity(req.getStockQuantity()).active(true).build();

        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(resp);

        ProductResponse result = service.update("1", req);
        assertEquals("1", result.getId());
        assertEquals("New", result.getName());
    }

    @Test
    void update_missing_throws() {
        when(repository.findById("x")).thenReturn(Optional.empty());
        ProductRequest req = ProductRequest.builder().name("n").price(new BigDecimal("1")).stockQuantity(1).build();
        assertThrows(ResourceNotFoundException.class, () -> service.update("x", req));
    }

    @Test
    void delete_existing_deletes() {
        when(repository.existsById("1")).thenReturn(true);
        service.delete("1");
        verify(repository).deleteById("1");
    }

    @Test
    void delete_missing_throws() {
        when(repository.existsById("x")).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete("x"));
    }
}
