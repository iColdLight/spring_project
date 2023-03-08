package com.coldlight.spring_project.dto;

import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.model.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto extends UserDto{
    private String status;

}
