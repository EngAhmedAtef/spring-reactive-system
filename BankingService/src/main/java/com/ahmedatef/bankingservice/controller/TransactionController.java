package com.ahmedatef.bankingservice.controller;

import com.ahmedatef.bankingservice.common.ControllerResponse;
import com.ahmedatef.bankingservice.dto.TransactionDTO;
import com.ahmedatef.bankingservice.enums.TransactionStatus;
import com.ahmedatef.bankingservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("banking/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @GetMapping
    public ResponseEntity<ControllerResponse<TransactionDTO>> getAllTransactions() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ControllerResponse<TransactionDTO>> getTransactionById(@PathVariable int id) {
        ControllerResponse<TransactionDTO> response = service.findById(id);
        HttpStatus status = response.getMessage() == null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("process")
    public ResponseEntity<ControllerResponse<TransactionDTO>> process(@RequestBody TransactionDTO dto) {
        LOGGER.info("Process transaction with details: {}", dto);
        ControllerResponse<TransactionDTO> processedTransactionResponse = service.process(dto);
        if (processedTransactionResponse.getData().get(0).getTransactionStatus().equals(TransactionStatus.SUCCESS))
            return new ResponseEntity<>(processedTransactionResponse, HttpStatus.OK);
        else
            return ResponseEntity.internalServerError().body(processedTransactionResponse);
    }
}
