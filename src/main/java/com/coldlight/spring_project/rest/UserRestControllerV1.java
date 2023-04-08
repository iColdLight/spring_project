package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.mapper.UserMapper;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping (value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = this.userService.getById(userId);
        if (userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto result = userMapper.toDto(userEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserEntity> users = this.userService.getAll();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UserDto> userDtos =
                users
                        .stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR', 'USER')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto){
        HttpHeaders headers = new HttpHeaders();
        if(userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userMapper.toEntity(userDto);
        this.userService.register(userEntity);
        return new ResponseEntity<>(userDto, headers, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();
        if(userDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userMapper.toEntity(userDto);
        this.userService.register(userEntity);
        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long userId){
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = this.userService.getById(userId);
        if (userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto result = userMapper.toDto(userEntity);
        this.userService.delete(userId);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }
}
