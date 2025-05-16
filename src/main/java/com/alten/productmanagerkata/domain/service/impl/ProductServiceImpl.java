package com.alten.productmanagerkata.domain.service.impl;

import com.alten.productmanagerkata.domain.exception.ProductAlreadyExistsException;
import com.alten.productmanagerkata.domain.exception.ProductNotFoundException;
import com.alten.productmanagerkata.domain.exception.UnauthorizedActionException;
import com.alten.productmanagerkata.domain.mapper.ProductMapper;
import com.alten.productmanagerkata.domain.model.Product;
import com.alten.productmanagerkata.domain.service.ProductService;
import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import com.alten.productmanagerkata.driven.persistence.repository.CartItemRepository;
import com.alten.productmanagerkata.driven.persistence.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public Product create(Product product) {
        checkAdminOnly();
        if (productRepository.existsByCode(product.getCode())) {
            throw new ProductAlreadyExistsException(product.getCode());
        }

        ProductEntity entity = productMapper.toEntity(product);
        Instant now = Instant.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        ProductEntity saved = productRepository.save(entity);
        Product result = productMapper.toDomain(saved);

        log.info("Produit créé avec le code : {}", result.getCode());
        return result;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDomain)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public Product update(Long id, Product product) {
        checkAdminOnly();
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (!entity.getCode().equals(product.getCode()) &&
                productRepository.existsByCode(product.getCode())) {
            throw new ProductAlreadyExistsException(product.getCode());
        }

        entity.setCode(product.getCode());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setCategory(product.getCategory());
        entity.setImage(product.getImage());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setInternalReference(product.getInternalReference());
        entity.setShellId(product.getShellId());
        entity.setInventoryStatus(ProductEntity.InventoryStatus.valueOf(product.getInventoryStatus().name()));
        entity.setRating(product.getRating());
        entity.setUpdatedAt(Instant.now());

        ProductEntity saved = productRepository.save(entity);
        Product result = productMapper.toDomain(saved);

        log.info("Produit mis à jour : {}", result.getCode());
        return result;

    }

    @Override
    public void delete(Long id) {
        checkAdminOnly();
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        if (cartItemRepository.existsByProductId(id)) {
            throw new UnauthorizedActionException("Ce produit est encore utilisé dans un panier");
        }
        productRepository.deleteById(id);

        log.info("Produit supprimé: {}", id);
    }

    private void checkAdminOnly() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!"admin@admin.com".equalsIgnoreCase(email)) {
            throw new UnauthorizedActionException("seul l'admin peut effectuer cette action");
        }
    }
}
