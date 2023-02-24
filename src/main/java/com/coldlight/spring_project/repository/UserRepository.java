package com.coldlight.spring_project.repository;

import com.coldlight.spring_project.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link UserEntity}.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String name);
}
