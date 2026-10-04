package com.banking.core_ledger.controller;


import com.banking.core_ledger.dto.TransferRequest;
import com.banking.core_ledger.service.TransferProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/transfers")
public class TransferController {

    // Injecting Kafka transfer producer
    private final TransferProducerService transferProducerService;

    @PostMapping("/send-funds")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest request){
        transferProducerService.sendTransferEvent(request);

        return ResponseEntity.ok("Transfer initiated and processing in background.");

    }

}
