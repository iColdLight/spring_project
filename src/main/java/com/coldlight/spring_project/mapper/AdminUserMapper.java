package com.coldlight.spring_project.mapper;

import com.coldlight.spring_project.dto.AdminUserDto;
import com.coldlight.spring_project.model.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminUserMapper {
    UserEntity toEntity(AdminUserDto source);

    AdminUserDto toDto(UserEntity source);
}
