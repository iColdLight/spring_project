package com.coldlight.spring_project.repository;

import com.coldlight.spring_project.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link EventEntity}.
 */
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
