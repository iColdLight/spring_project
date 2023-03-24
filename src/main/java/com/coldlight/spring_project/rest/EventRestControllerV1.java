package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.dto.EventDto;
import com.coldlight.spring_project.dto.FileDto;
import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.mapper.EventMapper;
import com.coldlight.spring_project.mapper.FileMapper;
import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
public class EventRestControllerV1 {

    @Autowired
    private EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EventDto> getEventById(@PathVariable("id") Long eventId) {
        if (eventId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EventEntity eventEntity = this.eventService.getById(eventId);
        if (eventEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EventDto result = eventMapper.toDto(eventEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EventDto>> getAllEvents(){
        List<EventEntity> events = this.eventService.getAll();
        if(events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<EventDto> eventDtos =
                events
                        .stream()
                        .map(eventMapper::toDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }
}
