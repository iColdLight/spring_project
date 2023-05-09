package com.coldlight.spring_project.rest;


import com.coldlight.spring_project.dto.FileDto;
import com.coldlight.spring_project.mapper.FileMapper;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.service.FileService;
import com.coldlight.spring_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
public class FileRestControllerV1 {

    @Autowired
    private FileService fileService;
    private UserService userService;
    private final FileMapper fileMapper;

    @GetMapping(value = "{id}")
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

    @GetMapping
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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> saveFile(@RequestPart MultipartFile file, @RequestPart Principal principal){
        HttpHeaders headers = new HttpHeaders();
        if(file == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String name = principal.getName();
        UserEntity userName = userService.findByUsername(name);
        this.fileService.save((FileEntity) file, String.valueOf(userName));
        return new ResponseEntity<>(file, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
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
