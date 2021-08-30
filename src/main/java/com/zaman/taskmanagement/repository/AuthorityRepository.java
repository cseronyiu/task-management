package com.zaman.taskmanagement.repository;

import com.zaman.taskmanagement.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
