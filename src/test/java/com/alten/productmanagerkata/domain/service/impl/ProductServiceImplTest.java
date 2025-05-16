package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.ProductAlreadyExistsException;
import com.alten.productmanagerkata.domain.exception.ProductNotFoundException;
import com.alten.productmanagerkata.domain.exception.UnauthorizedActionException;
import com.alten.productmanagerkata.domain.model.Product;
import com.alten.productmanagerkata.domain.service.ProductService;
import com.alten.productmanagerkata.driven.persistence.entity.CartItemEntity;
import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import com.alten.productmanagerkata.driven.persistence.repository.CartItemRepository;
import com.alten.productmanagerkata.driven.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
        productRepository.deleteAll();
        cartItemRepository.deleteAll();
    }

    private void setAdminUser() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("admin@admin.com", null)
        );
    }

    private void setRegularUser() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user@other.com", null)
        );
    }

    private Product sampleProduct(String code) {
        return Product.builder()
                .code(code)
                .name("Test")
                .description("desc")
                .category("cat")
                .image("img")
                .price(10.0)
                .quantity(5)
                .internalReference("ref")
                .shellId(1L)
                .inventoryStatus(Product.InventoryStatus.INSTOCK)
                .rating(5)
                .build();
    }

    @Test
    void shouldCreateProductSuccessfully() {
        setAdminUser();
        Product p = sampleProduct("C123");
        Product created = productService.create(p);

        assertNotNull(created.getId());
        assertEquals("C123", created.getCode());
    }

    @Test
    void shouldThrowIfProductCodeAlreadyExists() {
        setAdminUser();
        Product p1 = sampleProduct("PX");
        productService.create(p1);

        Product p2 = sampleProduct("PX");
        assertThrows(ProductAlreadyExistsException.class, () -> productService.create(p2));
    }

    @Test
    void shouldThrowIfNonAdminTriesToCreate() {
        setRegularUser();
        Product p = sampleProduct("ZZZ");
        assertThrows(UnauthorizedActionException.class, () -> productService.create(p));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        setAdminUser();
        Product p = productService.create(sampleProduct("UPD"));
        Product toUpdate = sampleProduct("UPD");
        toUpdate.setName("Updated Name");

        Product updated = productService.update(p.getId(), toUpdate);
        assertEquals("Updated Name", updated.getName());
    }

    @Test
    void shouldThrowOnUpdateWithExistingCode() {
        setAdminUser();
        productService.create(sampleProduct("CODE1"));
        Product p2 = productService.create(sampleProduct("CODE2"));

        Product conflict = sampleProduct("CODE1");
        assertThrows(ProductAlreadyExistsException.class, () -> productService.update(p2.getId(), conflict));
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        setAdminUser();
        Product p = productService.create(sampleProduct("DEL"));
        productService.delete(p.getId());

        assertFalse(productRepository.existsById(p.getId()));
    }


    @Test
    void shouldFindProductById() {
        setAdminUser();
        Product p = productService.create(sampleProduct("FIND"));
        Product found = productService.findById(p.getId());

        assertEquals("FIND", found.getCode());
    }

    @Test
    void shouldThrowIfProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> productService.findById(9999L));
    }

    @Test
    void shouldReturnAllProducts() {
        setAdminUser();
        productService.create(sampleProduct("ALL1"));
        productService.create(sampleProduct("ALL2"));

        List<Product> products = productService.findAll();
        assertEquals(2, products.size());
    }
}
