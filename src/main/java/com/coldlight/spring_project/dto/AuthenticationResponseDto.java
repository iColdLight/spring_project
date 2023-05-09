package com.coldlight.spring_project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDto {
    private String username;
    private String token;
}
