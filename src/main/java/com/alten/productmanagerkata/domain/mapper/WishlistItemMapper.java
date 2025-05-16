package com.alten.productmanagerkata.domain.mapper;

import com.alten.productmanagerkata.domain.model.WishlistItem;
import com.alten.productmanagerkata.driven.persistence.entity.WishlistItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistItemMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    WishlistItem toDomain(WishlistItemEntity entity);
}
