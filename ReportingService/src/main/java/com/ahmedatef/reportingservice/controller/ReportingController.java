package com.ahmedatef.reportingservice.controller;

import com.ahmedatef.reportingservice.dto.TransactionDTO;
import com.ahmedatef.reportingservice.enums.TransactionStatus;
import com.ahmedatef.reportingservice.service.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("report")
@Slf4j
@RequiredArgsConstructor
public class ReportingController {
    private final ReportingService service;

    @PostMapping
    public ResponseEntity<TransactionDTO> report(@RequestBody TransactionDTO dto) {
        log.info("Process transaction with details: {}", dto);
        TransactionDTO processed = service.report(dto);
        if (processed.getTransactionStatus().equals(TransactionStatus.SUCCESS))
            return new ResponseEntity<>(processed, HttpStatus.OK);
        else
            return ResponseEntity.internalServerError().body(processed);
    }
}
