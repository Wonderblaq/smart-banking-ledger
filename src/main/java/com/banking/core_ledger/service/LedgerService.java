package com.banking.core_ledger.service;


import com.banking.core_ledger.dto.TransferRequest;
import com.banking.core_ledger.entity.Account;
import com.banking.core_ledger.entity.TransactionRecord;
import com.banking.core_ledger.repository.AccountRepository;
import com.banking.core_ledger.repository.TransactionRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class LedgerService {

    // bean injection for stateless beans
    private final TransactionRecordRepository transactionRecordRepository;
    private final AccountRepository accountRepository;

    // Instantiate fresh transaction


    // function to perform a ledger annotated with Transactional for ACID op.
    @Transactional
    public void processTransfer(TransferRequest request){
        // find source account and destination account, return exception if not found
       Account sourceAcct =
               accountRepository.findByAccountNumber(
                request.getSourceAcctNumber()
                     ).orElseThrow(()-> new RuntimeException(
                             "Source account not found!"));


        Account destinationAcct =
                accountRepository
                        .findByAccountNumber(request.getDestinationAcctNumber())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Destination account not found"));


        BigDecimal sourceAcctBalance =
                sourceAcct.getBalance();
        // Check for insufficient funds
        if (sourceAcctBalance.compareTo(request.getAmount()) < 0){
            throw new RuntimeException(
                    "Insufficient funds"
            );
        }

        // Deduct amount from account balance
        BigDecimal amount =
                request.getAmount();
        sourceAcct.setBalance(
                sourceAcctBalance.subtract(
                        amount));


        // Add amount to destination account balance
        BigDecimal destinationAcctBalance =  destinationAcct.getBalance();
        destinationAcct.setBalance(
                destinationAcctBalance.add(
                        amount));

        // save accounts and their balance
        accountRepository.save(sourceAcct);
        accountRepository.save(destinationAcct);


        // Create Transaction history
        // Instantiate a NEW entity for this specific thread/transfer
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setTransactionDate(LocalDateTime.now());
        transactionRecord.setTransactionReference(UUID.randomUUID().toString());
        transactionRecord.setAmount(amount);
        transactionRecord.setStatus("COMPLETED");
        transactionRecord.setTransactionType("E-Transfer");
        transactionRecordRepository.save(transactionRecord);





    }





}
