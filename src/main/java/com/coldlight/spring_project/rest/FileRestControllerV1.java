package com.coldlight.spring_project.rest;


import com.coldlight.spring_project.dto.FileDto;
import com.coldlight.spring_project.mapper.FileMapper;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
public class FileRestControllerV1 {

    @Autowired
    private FileService fileService;
    private final FileMapper fileMapper;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileDto> getFileById(@PathVariable("id") Long fileId) {
        if (fileId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FileEntity fileEntity = this.fileService.getById(fileId);
        if (fileEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FileDto result = fileMapper.toDto(fileEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FileDto>> getAllFiles(){
        List<FileEntity> files = this.fileService.getAll();
        if(files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<FileDto> fileDtos =
                files
                        .stream()
                        .map(fileMapper::toDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(fileDtos, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileDto> saveFile(@RequestBody @Valid FileDto fileDto){
        HttpHeaders headers = new HttpHeaders();
        if(fileDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FileEntity fileEntity = fileMapper.toEntity(fileDto);
        this.fileService.save(fileEntity);
        return new ResponseEntity<>(fileDto, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileDto> updateFile(@RequestBody @Valid FileDto fileDto, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();
        if(fileDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FileEntity fileEntity = fileMapper.toEntity(fileDto);
        this.fileService.save(fileEntity);
        return new ResponseEntity<>(fileDto, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileDto> deleteFile(@PathVariable("id") Long fileId){
        if (fileId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FileEntity fileEntity = this.fileService.getById(fileId);
        if (fileEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FileDto result = fileMapper.toDto(fileEntity);
        this.fileService.delete(fileId);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }
}
