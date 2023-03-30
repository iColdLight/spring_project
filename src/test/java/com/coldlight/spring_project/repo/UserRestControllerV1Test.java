package com.coldlight.spring_project.repo;

import com.coldlight.spring_project.rest.UserRestControllerV1;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerV1Test {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRestControllerV1 userRestControllerV1;
}
