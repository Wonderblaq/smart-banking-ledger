package com.banking.core_ledger.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class TransferRequest {
    private String sourceAcctNumber;
    private String destinationAcctNumber;
    private BigDecimal amount;


}
