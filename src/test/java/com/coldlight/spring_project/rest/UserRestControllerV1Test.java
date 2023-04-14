

package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.mapper.UserMapper;
import com.coldlight.spring_project.model.RoleStatus;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.repository.UserRepository;
import com.coldlight.spring_project.support.DataSourceStub;
import com.coldlight.spring_project.support.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(value = {DataSourceStub.class, SecurityConfig.class})
public class UserRestControllerV1Test {
    @Autowired
    private UserRestControllerV1 userRestControllerV1;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    //@WithMockUser (roles = "USER")

    @Test
    public void Test() throws Exception {
        UserDto user = new UserDto();
        user.setFirstName("Igor");
        user.setLastName("Popovich");
        user.setUserName("IGPO");
        user.setPassword("qwerty123");
        user.setEmail("igor@gmail.com");

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        ResponseEntity responseEntity = objectMapper.convertValue(contentAsString, ResponseEntity.class);
    }
}
