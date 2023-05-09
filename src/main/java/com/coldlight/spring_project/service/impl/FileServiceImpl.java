package com.coldlight.spring_project.service.impl;

import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.model.EventStatus;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.repository.FileRepository;
import com.coldlight.spring_project.service.EventService;
import com.coldlight.spring_project.service.FileService;
import com.coldlight.spring_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserService userService;
    private final EventService eventService;


    @Override
    public void save(FileEntity fileEntity, String userName) {
        log.info("IN fileRepository save {}", fileEntity);
        UserEntity user = userService.findByUsername(userName);
        fileRepository.save(fileEntity);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setFile(fileEntity);
        eventEntity.setUser(user);
        eventEntity.setEventStatus(EventStatus.CREATED);
        eventEntity.setDate(new Date());
        eventService.save(eventEntity);
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
