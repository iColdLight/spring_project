package com.coldlight.spring_project.service;

import com.coldlight.spring_project.model.FileEntity;


import java.util.List;

/**
 * Service interface for class {@link com.coldlight.spring_project.model.FileEntity}.
 */
public interface FileService {

    void save (FileEntity fileEntity);

    void delete(Long id);

    List<FileEntity> getAll();

    FileEntity getById(Long id);
}
