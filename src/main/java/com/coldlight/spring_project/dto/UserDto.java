package com.coldlight.spring_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UserDto implements Serializable {
    private String userName;
    @NotEmpty
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
