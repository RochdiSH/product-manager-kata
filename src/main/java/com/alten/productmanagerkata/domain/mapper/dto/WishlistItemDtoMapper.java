package com.alten.productmanagerkata.domain.mapper.dto;

import com.alten.productmanagerkata.domain.model.WishlistItem;
import com.alten.productmanagerkata.driving.dto.WishlistItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistItemDtoMapper {

    WishlistItemResponse toResponse(WishlistItem item);
}
