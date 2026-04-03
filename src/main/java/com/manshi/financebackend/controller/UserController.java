package com.manshi.financebackend.controller;

import com.manshi.financebackend.entity.User;
import com.manshi.financebackend.security.JwtUtil;
import com.manshi.financebackend.service.UserService;
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

        // ✅ Proper validation
        if (dbUser == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        // ✅ Generate JWT token
        String token = JwtUtil.generateToken(
                dbUser.getEmail(),
                dbUser.getRole().name()   // ✅ CORRECT
        );

        return ResponseEntity.ok(token);
    }

    // 🔐 ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
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
}