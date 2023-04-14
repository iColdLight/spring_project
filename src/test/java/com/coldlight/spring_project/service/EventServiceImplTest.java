package com.coldlight.spring_project.service;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.model.EventEntity;
import com.coldlight.spring_project.model.EventStatus;
import com.coldlight.spring_project.model.FileEntity;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.repository.EventRepository;
import com.coldlight.spring_project.service.impl.EventServiceImpl;
import com.coldlight.spring_project.support.DataSourceStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(DataSourceStub.class)
public class EventServiceImplTest {
    @Autowired
    private EventServiceImpl eventService;

    @MockBean
    private EventRepository eventRepository;


    @Test
    public void getAllEventsTest() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .firstName("Igor")
                .lastName("Popovich")
                .userName("IGPO")
                .password("123456qwerty")
                .build();

        FileEntity fileEntity = FileEntity.builder()
                .id(1L)
                .name("FileName")
                .filePath("FilePath...")
                .build();

        FileEntity fileEntity2 = FileEntity.builder()
                .id(2L)
                .name("FileName2")
                .filePath("FilePath2...")
                .build();

        EventEntity event = EventEntity.builder()
                .eventStatus(EventStatus.CREATED)
                .user(userEntity)
                .file(fileEntity)
                .build();

        EventEntity event2 = EventEntity.builder()
                .eventStatus(EventStatus.CREATED)
                .user(userEntity)
                .file(fileEntity2)
                .build();

        //when
        when(eventRepository.findAll()).thenReturn(List.of(event, event2));

        //then
        List<EventEntity> allEvents = eventService.getAll();
        assertEquals(2, allEvents.size());

        EventEntity firstEvent = allEvents.get(0);
        assertEquals(userEntity, firstEvent.getUser());
        assertEquals(fileEntity, firstEvent.getFile());
        assertEquals(EventStatus.CREATED, firstEvent.getEventStatus());


        EventEntity secondEvent = allEvents.get(1);
        assertEquals(userEntity, secondEvent.getUser());
        assertEquals(fileEntity2, secondEvent.getFile());
        assertEquals(EventStatus.CREATED, secondEvent.getEventStatus());
    }

    @Test
    public void getEventsEmptyTest() {
        //when
        when(eventRepository.findAll()).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            eventService.getAll();
        });
    }

    @Test
    public void getFileByIDTest() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .firstName("Igor")
                .lastName("Popovich")
                .userName("IGPO")
                .password("123456qwerty")
                .build();

        FileEntity fileEntity = FileEntity.builder()
                .id(1L)
                .name("FileName")
                .filePath("FilePath...")
                .build();

        EventEntity event = EventEntity.builder()
                .eventStatus(EventStatus.CREATED)
                .user(userEntity)
                .file(fileEntity)
                .build();
        Long id = 1L;

        //when
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        //then
        EventEntity eventByID = eventService.getById(id);
        assertEquals(userEntity, eventByID.getUser());
        assertEquals(fileEntity, eventByID.getFile());
        assertEquals(EventStatus.CREATED, eventByID.getEventStatus());
    }

    @Test
    public void getFileByIDException() {
        //given
        Long id = 1L;

        //when
        when(eventRepository.findById(id)).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            eventService.getById(id);
        });
    }
}
