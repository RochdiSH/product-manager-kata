package com.alten.productmanagerkata.driving.rest.controller;

import com.alten.productmanagerkata.domain.mapper.dto.ProductDtoMapper;
import com.alten.productmanagerkata.domain.model.Product;
import com.alten.productmanagerkata.domain.service.ProductService;
import com.alten.productmanagerkata.driving.dto.CreateProductRequest;
import com.alten.productmanagerkata.driving.dto.UpdateProductRequest;
import com.alten.productmanagerkata.driving.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        Product created = productService.create(productDtoMapper.toDomain(request));
        return ResponseEntity.ok(productDtoMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(
                productService.findAll().stream()
                        .map(productDtoMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productDtoMapper.toResponse(productService.findById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateProductRequest request) {
        Product updated = productService.update(id, productDtoMapper.toDomain(request));
        return ResponseEntity.ok(productDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
