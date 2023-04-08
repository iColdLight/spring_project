package com.coldlight.spring_project.service;

import com.coldlight.spring_project.model.UserEntity;

import java.util.List;

/**
 * Service interface for class {@link com.coldlight.spring_project.model.UserEntity}.
 */
public interface UserService {

    UserEntity register (UserEntity userEntity);

    void delete(Long id);

    List<UserEntity> getAll();

    UserEntity getById(Long id);

    UserEntity findByUsername(String username);

}
