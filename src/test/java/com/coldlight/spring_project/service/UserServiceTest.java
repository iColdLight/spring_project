package com.coldlight.spring_project.service;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.repository.UserRepository;
import com.coldlight.spring_project.service.impl.UserServiceImpl;
import com.coldlight.spring_project.support.DataSourceStub;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(DataSourceStub.class)
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;

/*    @MockBean
    private UserRepository userRepository;*/

    @Captor
    ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @Test
    public void isUserCreatedTest(){
        UserEntity userEntity = new UserEntity();
        assertEquals(userEntity, userService.register(userEntity));
    }
/*
    @Test
    public void createUserTest() {
        //given
        String firstName = "Igor";
        String lastName = "Popovich";
        String userName = "IGPO";
        String password = "qwerty123";

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);

        //when
        when(userRepository.save(user)).thenReturn(user);

        //then
        assertEquals(user, userService.register(user));
    }
*/
}
