package com.coldlight.spring_project.service;

import com.coldlight.spring_project.Application;
import com.coldlight.spring_project.model.RoleStatus;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.repository.UserRepository;
import com.coldlight.spring_project.service.impl.UserServiceImpl;
import com.coldlight.spring_project.support.DataSourceStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = Application.class)
@Import(DataSourceStub.class)
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<Long> userEntityArgumentCaptor;

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
        assertEquals(RoleStatus.USER, user.getRoles().get(0).getRoleStatus());
    }

    @Test
    public void createUserExceptionTest() {
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
        when(userRepository.save(user)).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.register(user);
        });

    }

    @Test
    public void getAllUsersTest() {
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


        String firstName2 = "Bob";
        String lastName2 = "Bobovich";
        String userName2 = "BOB";
        String password2 = "qwerty458";

        UserEntity user2 = new UserEntity();
        user2.setFirstName(firstName2);
        user2.setLastName(lastName2);
        user2.setUserName(userName2);
        user2.setPassword(password2);

        //when
        when(userRepository.findAll()).thenReturn(List.of(user, user2));

        //then
        List<UserEntity> allUsers = userService.getAll();
        assertEquals(2, allUsers.size());

        UserEntity firstUser = allUsers.get(0);
        assertEquals(firstName, firstUser.getFirstName());
        assertEquals(lastName, firstUser.getLastName());
        assertEquals(userName, firstUser.getUserName());
        assertEquals(password, firstUser.getPassword());

        UserEntity secondUser = allUsers.get(1);
        assertEquals(firstName2, secondUser.getFirstName());
        assertEquals(lastName2, secondUser.getLastName());
        assertEquals(userName2, secondUser.getUserName());
        assertEquals(password2, secondUser.getPassword());
    }

    @Test
    public void getUsersEmptyTest() {
        //when
        when(userRepository.findAll()).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.getAll();
        });
    }

    @Test
    public void getUserByIDTest() {
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
        Long id = 1L;

        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        //then
        UserEntity userByID = userService.getById(id);
        assertEquals(firstName, userByID.getFirstName());
        assertEquals(lastName, userByID.getLastName());
        assertEquals(userName, userByID.getUserName());
        assertEquals(password, userByID.getPassword());
    }

    @Test
    public void getUserByIDException() {
        //given
        Long id = 1L;

        //when
        when(userRepository.findById(id)).thenThrow(new RuntimeException());

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.getById(id);
        });
    }

    @Test
    public void deleteUserTest() {
        String firstName = "Igor";
        String lastName = "Popovich";
        String userName = "IGPO";
        String password = "qwerty123";

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);
        Long id = 1L;

        //when
        when(userRepository.getById(1L)).thenReturn(user);
        doNothing().when(userRepository).deleteById(userEntityArgumentCaptor.capture());


        //then
        userService.delete(1L);
        Long value = userEntityArgumentCaptor.getValue();
        Assertions.assertEquals(1L, value.longValue());
    }

    @Test
    public void deleteUserNotFoundTest() {
        //when
        doThrow(RuntimeException.class).when(userRepository).deleteById(1L);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.delete(1L);
        });
    }

}
