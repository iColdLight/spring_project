package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Long userId){
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = this.userService.getById(userId);
        if (userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users = this.userService.getAll();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserEntity> saveUser(@RequestBody @Valid UserEntity userEntity){
        HttpHeaders headers = new HttpHeaders();
        if(userEntity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.register(userEntity);

        return new ResponseEntity<>(userEntity, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserEntity> updateUser(@RequestBody @Valid UserEntity userEntity, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();
        if(userEntity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.register(userEntity);
        return new ResponseEntity<>(userEntity, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserEntity> deleteUser(@PathVariable("id") Long userId){
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = this.userService.getById(userId);
        if (userEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.userService.delete(userId);
        return new ResponseEntity<>(userEntity, HttpStatus.NO_CONTENT);
    }
}
