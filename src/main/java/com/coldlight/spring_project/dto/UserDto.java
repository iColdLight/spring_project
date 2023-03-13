package com.coldlight.spring_project.dto;

import com.coldlight.spring_project.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
}
