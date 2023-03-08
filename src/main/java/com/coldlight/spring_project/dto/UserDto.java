package com.coldlight.spring_project.dto;

import com.coldlight.spring_project.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    public UserEntity toEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUserName(userName);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        return userEntity;
    }
    public static UserDto toDto(UserEntity userEntity){
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setUserName(userEntity.getUserName());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        return userDto;
    }
}
