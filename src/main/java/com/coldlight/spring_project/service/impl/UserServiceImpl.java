package com.coldlight.spring_project.service.impl;

import com.coldlight.spring_project.model.Role;
import com.coldlight.spring_project.model.UserEntity;
import com.coldlight.spring_project.model.UserStatus;
import com.coldlight.spring_project.repository.RoleRepository;
import com.coldlight.spring_project.repository.UserRepository;
import com.coldlight.spring_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity register(UserEntity userEntity) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(userRoles);
        userEntity.setUserStatus(UserStatus.ACTIVE);

        log.info("IN userRepository save {}", userEntity);
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        log.info("IN userRepository update {}", userEntity);
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("IN userRepository delete {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> getAll() {
        log.info("IN userRepository getAll");
        return userRepository.findAll();
    }

    @Override
    public UserEntity getById(Long id) {
        UserEntity result = userRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN getById - no user found by id: {}", id);
            return null;
        }
        log.info("IN getById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public UserEntity findByUsername(String username) {
        log.info("IN userRepository getByUsername {}", username);
        return userRepository.findByUserName(username);
    }
}
