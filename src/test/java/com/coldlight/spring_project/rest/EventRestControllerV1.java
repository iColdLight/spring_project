package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.dto.EventDto;
import com.coldlight.spring_project.dto.FileDto;
import com.coldlight.spring_project.mapper.EventMapper;
import com.coldlight.spring_project.mapper.FileMapper;
import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.model.EventStatus;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.support.DataSourceStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(value = DataSourceStub.class)
public class EventRestControllerV1 {

    @Autowired
    private  FileRestControllerV1 fileRestControllerV1;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    @Sql({"/mock_data.sql"})
    public void getEventByIdTest() throws Exception{
        EventEntity event = new EventEntity();
        event.setEventStatus(EventStatus.CREATED);
        List<EventEntity> events = new ArrayList<>();
        events.add(event);

        FileEntity file = new FileEntity();
        file.setName("File");
        file.setFilePath("File path");
        file.setEvents(events);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/files")
                        .content(objectMapper.writeValueAsString(file))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        /*mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/events/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventStatus").value(EventStatus.CREATED));*/
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql({"/mock_data.sql"})
    public void getEventsTest() throws Exception{
        EventEntity event = new EventEntity();
        event.setEventStatus(EventStatus.CREATED);
        List<EventEntity> events = new ArrayList<>();
        events.add(event);

        FileEntity file = new FileEntity();
        file.setName("File");
        file.setFilePath("File path");
        file.setEvents(events);

        EventEntity event2 = new EventEntity();
        event.setEventStatus(EventStatus.CREATED);
        List<EventEntity> events2 = new ArrayList<>();
        events.add(event2);

        FileEntity file2 = new FileEntity();
        file.setName("File2");
        file.setFilePath("File path2");
        file.setEvents(events2);

        EventDto eventDto = eventMapper.toDto(event);
        EventDto eventDto2 = eventMapper.toDto(event2);

        FileDto fileDto = fileMapper.toDto(file);
        FileDto fileDto2 = fileMapper.toDto(file2);

        //fileRestControllerV1.saveFile(fileDto);
        //fileRestControllerV1.saveFile(fileDto2);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/events")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventStatus").value(EventStatus.CREATED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].eventStatus").value(EventStatus.CREATED));

    }
}

