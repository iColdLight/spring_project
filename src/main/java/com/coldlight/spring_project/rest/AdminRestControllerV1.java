package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.dto.AdminUserDto;
import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.mapper.AdminUserMapper;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final AdminUserMapper adminUserMapper;


    @GetMapping(value = "user/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable("id") Long userId){
        UserEntity userEntity = this.userService.getById(userId);
        if (userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdminUserDto result = adminUserMapper.toDto(userEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserEntity> users = this.userService.getAll();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UserDto> userDtos =
                users
                        .stream()
                        .map(adminUserMapper::toDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AdminUserDto> saveUser(@RequestBody @Valid AdminUserDto adminUserDto){
        HttpHeaders headers = new HttpHeaders();
        if(adminUserDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = adminUserMapper.toEntity(adminUserDto);
        this.userService.register(userEntity);
        return new ResponseEntity<>(adminUserDto, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AdminUserDto> updateUser(@RequestBody @Valid AdminUserDto adminUserDto, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();
        if(adminUserDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = adminUserMapper.toEntity(adminUserDto);
        this.userService.register(userEntity);
        return new ResponseEntity<>(adminUserDto, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AdminUserDto> deleteUser(@PathVariable("id") Long userId){
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = this.userService.getById(userId);
        if (userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdminUserDto result = adminUserMapper.toDto(userEntity);
        this.userService.delete(userId);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }
}
