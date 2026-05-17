package com.example.productservice.controller;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void create_returnsCreated() throws Exception {
        ProductRequest req = ProductRequest.builder().name("P").price(new BigDecimal("5.00")).stockQuantity(3).active(true).build();
        ProductResponse resp = ProductResponse.builder().id("1").name("P").price(req.getPrice()).stockQuantity(3).active(true).build();
        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void getAll_returnsList() throws Exception {
        ProductResponse resp = ProductResponse.builder().id("1").name("P").build();
        when(service.getAll()).thenReturn(List.of(resp));

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getById_returnsItem() throws Exception {
        ProductResponse resp = ProductResponse.builder().id("1").name("P").build();
        when(service.getById("1")).thenReturn(resp);

        mvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }
}
