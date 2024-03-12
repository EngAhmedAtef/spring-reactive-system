package com.ahmedatef.accountmanagementservice.controller;

import com.ahmedatef.accountmanagementservice.dto.TransactionDTO;
import com.ahmedatef.accountmanagementservice.enums.TransactionStatus;
import com.ahmedatef.accountmanagementservice.service.AccountManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountManagementController {
    private final AccountManagementService service;

    @PostMapping("process")
    public ResponseEntity<TransactionDTO> manage(@RequestBody TransactionDTO dto) {
        log.info("Process transaction with details: {}", dto);
        TransactionDTO processed = service.manage(dto);
        if (processed.getTransactionStatus().equals(TransactionStatus.SUCCESS))
            return ResponseEntity.ok(processed);
        else
            return ResponseEntity.internalServerError().body(processed);
    }
}
