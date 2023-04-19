package com.coldlight.spring_project.service.impl;

import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.repository.EventRepository;
import com.coldlight.spring_project.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void save(EventEntity eventEntity) {
        log.info("IN eventRepository save {}", eventEntity);
        eventRepository.save(eventEntity);
    }
    @Override
    public List<EventEntity> getAll() {
        log.info("IN eventRepository getAll");
        return eventRepository.findAll();
    }

    @Override
    public EventEntity getById(Long id) {
        EventEntity result = eventRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN getById - no event found by id: {}", id);
            return null;
        }
        log.info("IN getById - event: {} found by id: {}", result, id);
        return result;
    }
}
