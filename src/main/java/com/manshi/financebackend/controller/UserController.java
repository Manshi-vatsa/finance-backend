package com.manshi.financebackend.controller;

import com.manshi.financebackend.entity.User;
import com.manshi.financebackend.security.AuthorizationUtil;
import com.manshi.financebackend.security.JwtUtil;
import com.manshi.financebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔓 LOGIN (No token required)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User dbUser = userService.findByEmail(user.getEmail());

        if (dbUser == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (!userService.checkPassword(user.getPassword(), dbUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        String token = JwtUtil.generateToken(
                dbUser.getEmail(),
                dbUser.getRole().name()
        );

        return ResponseEntity.ok(token);
    }

    // 🔐 CREATE USER
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        // ✅ First user → allow without token
        if (userService.getUserCount() == 0) {
            user.setRole(user.getRole() != null ? user.getRole() : com.manshi.financebackend.enums.Role.ADMIN);
            user.setStatus(user.getStatus() != null ? user.getStatus() : com.manshi.financebackend.enums.Status.ACTIVE);
            return ResponseEntity.ok(userService.createUser(user));
        }

        // ✅ After that → only ADMIN
        if (!AuthorizationUtil.isAdmin()) {
            return ResponseEntity.status(403).body("Only ADMIN can create users");
        }

        return ResponseEntity.ok(userService.createUser(user));
    }

    // 🔐 ADMIN + ANALYST
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/insights")
    public ResponseEntity<?> getInsights() {
        return ResponseEntity.ok("Insights Data");
    }

    // 🔐 ALL ROLES
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        return ResponseEntity.ok("Dashboard Data");
    }
    // ✅ PAGINATION
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers(org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }
}