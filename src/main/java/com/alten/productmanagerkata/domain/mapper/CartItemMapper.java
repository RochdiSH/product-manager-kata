package com.alten.productmanagerkata.domain.mapper;

import com.alten.productmanagerkata.domain.model.CartItem;
import com.alten.productmanagerkata.driven.persistence.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    CartItem toDomain(CartItemEntity entity);
}
