package com.coldlight.spring_project.mapper;

import com.coldlight.spring_project.dto.EventDto;
import com.coldlight.spring_project.model.EventEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {
    EventEntity toEntity(EventDto source);

    EventDto toDto(EventEntity source);
}
