package com.manshi.financebackend.controller;

import com.manshi.financebackend.entity.FinancialRecord;
import com.manshi.financebackend.entity.User;
import com.manshi.financebackend.enums.RecordType;
import com.manshi.financebackend.security.AuthorizationUtil;
import com.manshi.financebackend.service.FinancialRecordService;
import com.manshi.financebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService service;
    private final UserService userService;

    // ✅ ADMIN ONLY → Create
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FinancialRecord> create(@RequestBody FinancialRecord record) {

        String email = AuthorizationUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);

        record.setCreatedBy(user);

        return ResponseEntity.ok(service.createRecord(record));
    }

    // ✅ ALL ROLES → Read + Filter
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping
    public ResponseEntity<List<FinancialRecord>> getAll(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(service.filterRecords(type, category, date));
    }

    // ✅ ADMIN ONLY → Update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FinancialRecord> update(@PathVariable Long id,
                                                  @RequestBody FinancialRecord record) {
        return ResponseEntity.ok(service.updateRecord(id, record));
    }

    // ✅ ADMIN ONLY → Delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // ✅ SUMMARY API (ADMIN + ANALYST)
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getSummary(userId));
    }
}