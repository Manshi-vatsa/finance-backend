package com.manshi.financebackend.service;

import com.manshi.financebackend.dto.SummaryResponse;
import com.manshi.financebackend.entity.FinancialRecord;
import com.manshi.financebackend.enums.RecordType;
import com.manshi.financebackend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository repository;

    // ✅ CREATE
    public FinancialRecord createRecord(FinancialRecord record) {
        return repository.save(record);
    }

    // ✅ GET ALL
    public List<FinancialRecord> getAllRecords() {
        return repository.findAll();
    }

    // ✅ UPDATE
    public FinancialRecord updateRecord(Long id, FinancialRecord updated) {
        FinancialRecord record = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setAmount(updated.getAmount());
        record.setType(updated.getType());
        record.setCategory(updated.getCategory());
        record.setDate(updated.getDate());
        record.setNotes(updated.getNotes());

        return repository.save(record);
    }

    // ✅ DELETE
    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }

    // 🔥 SUMMARY API
    public SummaryResponse getSummary(Long userId) {

        // ✅ Optional: get summary only for a user
        List<FinancialRecord> records = repository.findByCreatedBy_Id(userId);

        double totalIncome = 0;
        double totalExpense = 0;

        for (FinancialRecord record : records) {
            Double amt = record.getAmount();
            if (amt == null) continue;

            if (record.getType() == RecordType.INCOME) {
                totalIncome += amt;
            } else if (record.getType() == RecordType.EXPENSE) {
                totalExpense += amt;
            }
        }

        double balance = totalIncome - totalExpense;

        return new SummaryResponse(totalIncome, totalExpense, balance);
    }

    // 🔍 FILTER LOGIC
    public List<FinancialRecord> filterRecords(RecordType type, String category, LocalDate date) {

        if (type != null && category != null && date != null) {
            return repository.findByTypeAndCategoryAndDate(type, category, date);
        } else if (type != null && category != null) {
            return repository.findByTypeAndCategory(type, category);
        } else if (type != null) {
            return repository.findByType(type);
        } else if (category != null) {
            return repository.findByCategory(category);
        } else if (date != null) {
            return repository.findByDate(date);
        } else {
            return repository.findAll();
        }
    }
}