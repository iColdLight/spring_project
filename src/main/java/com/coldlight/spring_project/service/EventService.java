package com.coldlight.spring_project.service;

import com.coldlight.spring_project.model.EventEntity;

import java.util.List;

/**
 * Service interface for class {@link com.coldlight.spring_project.model.EventEntity}.
 */
public interface EventService {

    List<EventEntity> getAll();

    EventEntity getById(Long id);
}
