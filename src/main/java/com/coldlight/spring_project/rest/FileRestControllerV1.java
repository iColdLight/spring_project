package com.coldlight.spring_project.rest;


import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.service.FileService;
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

@RestController
@RequestMapping("/api/v1/files")
@Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
public class FileRestControllerV1 {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileEntity> getFileById(@PathVariable("id") Long fileId) {
        if (fileId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FileEntity fileEntity = this.fileService.getById(fileId);
        if (fileEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fileEntity, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FileEntity>> getAllFiles(){
        List<FileEntity> files = this.fileService.getAll();
        if(files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileEntity> saveFile(@RequestBody @Valid FileEntity fileEntity){
        HttpHeaders headers = new HttpHeaders();
        if(fileEntity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.fileService.save(fileEntity);

        return new ResponseEntity<>(fileEntity, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileEntity> updateFile(@RequestBody @Valid FileEntity fileEntity, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();
        if(fileEntity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.fileService.save(fileEntity);
        return new ResponseEntity<>(fileEntity, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FileEntity> deleteFile(@PathVariable("id") Long fileId){
        if (fileId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FileEntity fileEntity = this.fileService.getById(fileId);
        if (fileEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.fileService.delete(fileId);
        return new ResponseEntity<>(fileEntity, HttpStatus.NO_CONTENT);
    }
}
