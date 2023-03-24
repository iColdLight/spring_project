package com.coldlight.spring_project.dto;

import com.coldlight.spring_project.model.EventStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    private Date date;
    private EventStatus fileStatus;
}
