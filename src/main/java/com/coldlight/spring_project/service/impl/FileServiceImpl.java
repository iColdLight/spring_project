package com.coldlight.spring_project.service.impl;

import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.repository.FileRepository;
import com.coldlight.spring_project.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void save(FileEntity fileEntity) {
        log.info("IN fileRepository save {}", fileEntity);
        fileRepository.save(fileEntity);
    }

    @Override
    public void delete(Long id) {
        log.info("IN fileRepository delete {}", id);
        fileRepository.deleteById(id);
    }

    @Override
    public List<FileEntity> getAll() {
        log.info("IN fileRepository getAll");
        return fileRepository.findAll();
    }

    @Override
    public FileEntity getById(Long id) {
        FileEntity result = fileRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN getById - no file found by id: {}", id);
            return null;
        }
        log.info("IN getById - file: {} found by id: {}", result, id);
        return result;
    }
}
