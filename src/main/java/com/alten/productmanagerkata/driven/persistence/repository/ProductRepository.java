package com.alten.productmanagerkata.driven.persistence.repository;


import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByCode(String code);
}
