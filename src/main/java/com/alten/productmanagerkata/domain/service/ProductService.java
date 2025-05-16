package com.alten.productmanagerkata.domain.service;


import com.alten.productmanagerkata.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(Product product);

    Product findById(Long id);

    List<Product> findAll();

    Product update(Long id, Product product);

    void delete(Long id);
}
