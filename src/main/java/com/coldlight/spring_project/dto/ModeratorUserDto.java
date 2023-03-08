package com.coldlight.spring_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModeratorUserDto extends UserDto {

    private String status;

}
