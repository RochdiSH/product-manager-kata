package com.alten.productmanagerkata.domain.mapper.dto;

import com.alten.productmanagerkata.domain.model.CartItem;
import com.alten.productmanagerkata.driving.dto.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemDtoMapper {
    CartItemResponse toResponse(CartItem cartItem);
}
