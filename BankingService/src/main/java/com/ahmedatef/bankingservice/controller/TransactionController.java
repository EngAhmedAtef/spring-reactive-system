package com.ahmedatef.bankingservice.controller;

import com.ahmedatef.bankingservice.common.ControllerResponse;
import com.ahmedatef.bankingservice.dto.TransactionDTO;
import com.ahmedatef.bankingservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

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
}
