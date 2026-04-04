package com.manshi.financebackend.entity;

import com.manshi.financebackend.enums.RecordType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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


    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;
}