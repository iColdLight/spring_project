package com.coldlight.spring_project.mapper;

import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.model.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserEntity toEntity(UserDto source);

    UserDto toDto(UserEntity source);




}
