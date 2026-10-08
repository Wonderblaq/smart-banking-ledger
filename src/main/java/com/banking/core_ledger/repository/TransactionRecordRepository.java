package com.banking.core_ledger.repository;

import com.banking.core_ledger.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {

}
