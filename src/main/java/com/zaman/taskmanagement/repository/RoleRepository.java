package com.zaman.taskmanagement.repository;

import com.zaman.taskmanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
