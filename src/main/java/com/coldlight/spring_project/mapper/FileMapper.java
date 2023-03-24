package com.coldlight.spring_project.mapper;

import com.coldlight.spring_project.dto.FileDto;
import com.coldlight.spring_project.model.FileEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FileMapper {
    FileEntity toEntity(FileDto source);

    FileDto toDto(FileEntity source);
}
