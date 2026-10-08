package com.banking.core_ledger.service;


import com.banking.core_ledger.dto.TransferRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransferConsumerService {
    private final LedgerService ledgerService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "transfer-requests",
            groupId = "core-ledger-group", concurrency = "3")
    public void consumeTransferEvent(String message) throws JsonProcessingException {
        // convert from JSON to java/spring object
        TransferRequest request = objectMapper.
                readValue(
                        message, TransferRequest.class);
        try {
            ledgerService.
                    processTransfer(
                            request);
            log.info("Request Processed Successfully for: {}",
                    request.getSourceAcctNumber());

        }catch (
                RuntimeException
                        e){
            log.error("Could not process request",
                    e.getMessage());


        }

    }
}
