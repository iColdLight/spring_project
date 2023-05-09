

package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.dto.UserDto;
import com.coldlight.spring_project.mapper.UserMapper;
import com.coldlight.spring_project.model.UserEntity;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(roles = "USER", username = "IGPO")
    @Sql({"/mock_data.sql"})
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
    @Sql({"/mock_data.sql"})
    public void getUserByIdTest() throws Exception{
        /*UserDto user = new UserDto();
        user.setFirstName("Igor");
        user.setLastName("Popovich");
        user.setUserName("IGPO");
        user.setPassword("qwerty123");
        user.setEmail("igor@gmail.com");*/

        /*mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated());*/

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Igor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Popovich"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("IGPO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("igor123@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists());
    }

    @Test
    @WithMockUser(roles = "USER")
    @Sql({"/mock_data.sql"})
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

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Igor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName").value("Popovich"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userName").value("IGPO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value("igor@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].password").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("Carl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userName").value("CS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].email").value("carl@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].password").exists());
    }

    @Test
    @WithMockUser(roles = "USER", username = "IGPO")
    @Sql({"/mock_data.sql"})
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
