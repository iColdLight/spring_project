package com.coldlight.spring_project.service;

import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.model.FileEntity;

import java.util.List;

/**
 * Service interface for class {@link com.coldlight.spring_project.model.EventEntity}.
 */
public interface EventService {

    void save (EventEntity eventEntity);
    List<EventEntity> getAll();

    EventEntity getById(Long id);
}
