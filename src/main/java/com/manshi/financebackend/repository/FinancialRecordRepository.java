package com.manshi.financebackend.repository;

import com.manshi.financebackend.entity.FinancialRecord;
import com.manshi.financebackend.enums.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByType(RecordType type);

    List<FinancialRecord> findByCategory(String category);

    List<FinancialRecord> findByDate(LocalDate date);

    List<FinancialRecord> findByTypeAndCategory(RecordType type, String category);

    List<FinancialRecord> findByTypeAndCategoryAndDate(RecordType type, String category, LocalDate date);

    List<FinancialRecord> findByCreatedBy_Id(Long userId);
}