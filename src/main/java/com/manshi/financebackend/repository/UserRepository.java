package com.manshi.financebackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.manshi.financebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    // ✅ ADD THIS
    long countByRole(com.manshi.financebackend.enums.Role role);
}