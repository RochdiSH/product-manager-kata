package com.alten.productmanagerkata.domain.mapper.dto;

import com.alten.productmanagerkata.domain.model.Product;
import com.alten.productmanagerkata.driving.dto.CreateProductRequest;
import com.alten.productmanagerkata.driving.dto.UpdateProductRequest;
import com.alten.productmanagerkata.driving.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toDomain(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toDomain(UpdateProductRequest request);
    ProductResponse toResponse(Product product);
}
