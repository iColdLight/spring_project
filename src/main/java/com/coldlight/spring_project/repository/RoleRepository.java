package com.coldlight.spring_project.repository;

import com.coldlight.spring_project.model.Role;
import com.coldlight.spring_project.model.RoleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link Role}.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleStatus(RoleStatus roleStatus);
}
