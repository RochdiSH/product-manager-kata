package com.alten.productmanagerkata.domain.mapper;

import com.alten.productmanagerkata.domain.model.User;
import com.alten.productmanagerkata.driven.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User domain);
    User toDomain(UserEntity entity);
}
