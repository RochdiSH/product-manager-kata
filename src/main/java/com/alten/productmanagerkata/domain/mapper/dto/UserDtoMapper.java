package com.alten.productmanagerkata.domain.mapper.dto;

import com.alten.productmanagerkata.domain.model.User;
import com.alten.productmanagerkata.driving.dto.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toDomain(RegisterRequest request);
}