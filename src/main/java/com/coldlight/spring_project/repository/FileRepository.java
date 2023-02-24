package com.coldlight.spring_project.repository;

import com.coldlight.spring_project.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link FileEntity}.
 */
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
