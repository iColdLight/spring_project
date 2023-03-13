package com.coldlight.spring_project.mapper;

import com.coldlight.spring_project.dto.ModeratorUserDto;
import com.coldlight.spring_project.model.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ModeratorUserMapper {
    UserEntity toEntity(ModeratorUserDto source);

    ModeratorUserDto toDto(UserEntity source);
}
