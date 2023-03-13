package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
public class EventRestControllerV1 {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EventEntity> getEventById(@PathVariable("id") Long eventId) {
        if (eventId == null) {
            return new ResponseEntity<EventEntity>(HttpStatus.BAD_REQUEST);
        }
        EventEntity eventEntity = this.eventService.getById(eventId);
        if (eventEntity == null) {
            return new ResponseEntity<EventEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<EventEntity>(eventEntity, HttpStatus.OK);
    }
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EventEntity>> getAllEvents(){
        List<EventEntity> events = this.eventService.getAll();
        if(events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
