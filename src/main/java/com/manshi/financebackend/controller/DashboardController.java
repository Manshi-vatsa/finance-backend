package com.manshi.financebackend.controller;

import com.manshi.financebackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    // ✅ SUMMARY
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary")
    public ResponseEntity<?> summary(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getSummary(userId));
    }

    // ✅ CATEGORY
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/category-summary")
    public ResponseEntity<?> category(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getCategorySummary(userId));
    }

    // ✅ RECENT
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/recent")
    public ResponseEntity<?> recent(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getRecent(userId));
    }

    // ✅ MONTHLY
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/monthly")
    public ResponseEntity<?> monthly(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getMonthly(userId));
    }
}