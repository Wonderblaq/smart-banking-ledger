package com.banking.core_ledger.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String transactionReference;

    private String sourceAccountNumber;

    private String destinationAccountNumber;

    private String transactionType;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    private LocalDateTime transactionDate;

    private String status;
}