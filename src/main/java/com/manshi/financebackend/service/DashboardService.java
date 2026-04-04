package com.manshi.financebackend.service;

import com.manshi.financebackend.entity.FinancialRecord;
import com.manshi.financebackend.enums.RecordType;
import com.manshi.financebackend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository repository;

    // ✅ SUMMARY (reuse logic)
    public Map<String, Double> getSummary(Long userId) {

        List<FinancialRecord> records = repository.findByCreatedBy_Id(userId);

        double income = 0;
        double expense = 0;

        for (FinancialRecord r : records) {
            if (r.getAmount() == null) continue;

            if (r.getType() == RecordType.INCOME) income += r.getAmount();
            else expense += r.getAmount();
        }

        Map<String, Double> result = new HashMap<>();
        result.put("totalIncome", income);
        result.put("totalExpense", expense);
        result.put("balance", income - expense);

        return result;
    }

    // ✅ CATEGORY SUMMARY
    public Map<String, Double> getCategorySummary(Long userId) {

        List<FinancialRecord> records = repository.findByCreatedBy_Id(userId);

        Map<String, Double> map = new HashMap<>();

        for (FinancialRecord r : records) {
            if (r.getCategory() == null || r.getAmount() == null) continue;

            map.put(r.getCategory(),
                    map.getOrDefault(r.getCategory(), 0.0) + r.getAmount());
        }

        return map;
    }

    // ✅ RECENT TRANSACTIONS (last 5)
    public List<FinancialRecord> getRecent(Long userId) {

        List<FinancialRecord> records = repository.findByCreatedBy_Id(userId);

        records.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        return records.size() > 5 ? records.subList(0, 5) : records;
    }

    // ✅ MONTHLY TREND
    public Map<String, Double> getMonthly(Long userId) {

        List<FinancialRecord> records = repository.findByCreatedBy_Id(userId);

        Map<String, Double> map = new HashMap<>();

        for (FinancialRecord r : records) {
            if (r.getDate() == null || r.getAmount() == null) continue;

            String month = r.getDate().getMonth().toString();

            map.put(month,
                    map.getOrDefault(month, 0.0) + r.getAmount());
        }

        return map;
    }
}