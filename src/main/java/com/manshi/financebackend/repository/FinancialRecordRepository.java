package com.manshi.financebackend.repository;

import com.manshi.financebackend.entity.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
}