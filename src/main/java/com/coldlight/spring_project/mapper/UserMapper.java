package com.coldlight.spring_project.mapper;

import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity toEntity(UserDto userDto) {
        return Objects.isNull(userDto) ? null : modelMapper.map(userDto, UserEntity.class);
    }

    public UserDto toDto(UserEntity user) {

        return Objects.isNull(user) ? null : modelMapper.map(user, UserDto.class);
    }
}
