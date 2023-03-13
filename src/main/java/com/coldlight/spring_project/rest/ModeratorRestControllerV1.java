package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.dto.AdminUserDto;
import com.coldlight.spring_project.dto.ModeratorUserDto;
import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.mapper.AdminUserMapper;
import com.coldlight.spring_project.mapper.ModeratorUserMapper;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/moderators/")
public class ModeratorRestControllerV1 {
    private final UserService userService;
    private final ModeratorUserMapper moderatorUserMapper;

    @GetMapping(value = "user/{id}")
    public ResponseEntity<ModeratorUserDto> getUserById(@PathVariable("id") Long userId){
        UserEntity userEntity = this.userService.getById(userId);
        if (userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ModeratorUserDto result = moderatorUserMapper.toDto(userEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ModeratorUserDto>> getAllUsers(){
        List<UserEntity> users = this.userService.getAll();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ModeratorUserDto> userDtos =
                users
                        .stream()
                        .map(moderatorUserMapper::toDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

}
