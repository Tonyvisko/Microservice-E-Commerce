package com.example.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

	@NotBlank
	private String name;

	private String description;

	@NotNull
	private BigDecimal price;

	@NotNull
	private Integer stockQuantity;

	private String category;

	private String imageUrl;

	private Boolean active;

}
