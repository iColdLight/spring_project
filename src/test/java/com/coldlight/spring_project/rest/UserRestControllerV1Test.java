

package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.mapper.UserMapper;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.repository.UserRepository;
import com.coldlight.spring_project.support.DataSourceStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(value = DataSourceStub.class)
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


    @Test
    @WithMockUser(roles = "USER", username = "IGPO")
    public void saveUserTest() throws Exception {
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
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse().getContentAsString();
        UserDto result = objectMapper.readValue(contentAsString, UserDto.class);

        assertEquals("Igor", result.getFirstName());
        assertEquals("Popovich", result.getLastName());
        assertEquals("IGPO", result.getUserName());
        assertEquals("qwerty123", result.getPassword());
        assertEquals("igor@gmail.com", result.getEmail());
    }
    @Test
    @WithMockUser(roles = "USER")
    public void getUserByIdTest() throws Exception{
        UserDto user = new UserDto();
        user.setFirstName("Igor");
        user.setLastName("Popovich");
        user.setUserName("IGPO");
        user.setPassword("qwerty123");
        user.setEmail("igor@gmail.com");

        userRestControllerV1.saveUser(user);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/1")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        UserDto result = objectMapper.readValue(contentAsString, UserDto.class);

        assertEquals("Igor", result.getFirstName());
        assertEquals("Popovich", result.getLastName());
        assertEquals("IGPO", result.getUserName());
        assertNotNull( result.getPassword());
        assertEquals("igor@gmail.com", result.getEmail());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getUsersTest() throws Exception{
        UserEntity user = new UserEntity();
        user.setFirstName("Igor");
        user.setLastName("Popovich");
        user.setUserName("IGPO");
        user.setPassword("qwerty123");
        user.setEmail("igor@gmail.com");
        user.setId(1L);

        UserEntity user2 = new UserEntity();
        user2.setFirstName("Carl");
        user2.setLastName("Smith");
        user2.setUserName("CS");
        user2.setPassword("gfdsa456");
        user2.setEmail("carl@gmail.com");
        user2.setId(2L);

        UserDto userDto = userMapper.toDto(user);
        UserDto userDto2 = userMapper.toDto(user2);

        userRestControllerV1.saveUser(userDto);
        userRestControllerV1.saveUser(userDto2);

        Map<String, UserDto> map = new HashMap<>();
        map.put("User1", userDto);
        map.put("User2", userDto2);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .content(objectMapper.writeValueAsString(map))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        UserDto result = objectMapper.readValue(contentAsString, UserDto.class);

        ResponseEntity<List<UserDto>> allUsers = userRestControllerV1.getAllUsers();
        List<UserDto> allUsersResult = objectMapper.readValue(allUsers, ResponseEntity.class);

        assertEquals("Igor", result.getFirstName());
        assertEquals("Popovich", result.getLastName());
        assertEquals("IGPO", result.getUserName());
        assertNotNull( result.getPassword());
        assertEquals("igor@gmail.com", result.getEmail());

    }

    @Test
    @WithMockUser(roles = "USER", username = "IGPO")
    public void deleteUserTest() throws Exception {
        UserDto user = new UserDto();
        user.setFirstName("Igor");
        user.setLastName("Popovich");
        user.setUserName("IGPO");
        user.setPassword("qwerty123");
        user.setEmail("igor@gmail.com");

        RequestBuilder request = MockMvcRequestBuilders
                        .delete("/api/v1/users");
        mockMvc.perform(request).andReturn();
    }
}
