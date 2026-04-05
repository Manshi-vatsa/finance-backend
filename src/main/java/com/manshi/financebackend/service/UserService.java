package com.manshi.financebackend.service;

import com.manshi.financebackend.entity.User;
import com.manshi.financebackend.enums.Role;
import com.manshi.financebackend.repository.UserRepository;
import com.manshi.financebackend.security.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public long getUserCount() {
        return userRepository.count();
    }

    // ✅ Create user with encrypted password
    public User createUser(User user) {
        long adminCount = userRepository.countByRole(Role.ADMIN);

        // 🔥 ADD THIS LINE (MOST IMPORTANT)
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (adminCount == 0) {
            return userRepository.save(user);
        }

        if (!AuthorizationUtil.isAdmin()) {
            throw new RuntimeException("Only ADMIN can create users");
        }

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}