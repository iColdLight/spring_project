package com.coldlight.spring_project.rest;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.dto.UserDto;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(value = DataSourceStub.class)
public class AuthenticationRestControllerV1Test {
    @Autowired
    private AuthenticationRestControllerV1 authenticationRestControllerV1;
    @Autowired
    private UserRestControllerV1 userRestControllerV1;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @WithMockUser(roles = "USER", username = "IGPO")
    @Sql({"/mock_data.sql"})
    public void loginTest() throws Exception{
        UserDto user = new UserDto();
        user.setFirstName("Igor");
        user.setLastName("Popovich");
        user.setUserName("IGPO");
        user.setPassword("qwerty123");
        user.setEmail("igor@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(formLogin().user("IGPO").password("qwerty123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/v1/auth/login"));
    }
}
