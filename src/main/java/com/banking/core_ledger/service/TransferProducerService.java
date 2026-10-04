package com.banking.core_ledger.service;


import com.banking.core_ledger.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "transfer-requests";

    public void sendTransferEvent(TransferRequest request){
        try {
            String jsonMessage = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize transfer request", e);
            throw new RuntimeException("Unable to process transfer request");
        }
    }

}
