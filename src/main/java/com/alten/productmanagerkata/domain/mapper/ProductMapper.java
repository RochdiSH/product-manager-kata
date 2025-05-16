package com.alten.productmanagerkata.domain.mapper;

import com.alten.productmanagerkata.domain.model.Product;
import com.alten.productmanagerkata.driven.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(Product domain);
    Product toDomain(ProductEntity entity);
}
