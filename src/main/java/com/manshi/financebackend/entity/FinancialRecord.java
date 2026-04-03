package com.manshi.financebackend.entity;

import com.manshi.financebackend.enums.RecordType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private RecordType type;

    private String category;

    private LocalDate date;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}