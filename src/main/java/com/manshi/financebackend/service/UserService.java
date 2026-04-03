package com.manshi.financebackend.service;

import com.manshi.financebackend.entity.User;
import com.manshi.financebackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);   // ✅ Correct
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);   // ✅ Correct
    }
}