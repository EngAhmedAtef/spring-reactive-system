package com.ahmedatef.reportingservice.controller;

import com.ahmedatef.reportingservice.dto.TransactionDTO;
import com.ahmedatef.reportingservice.service.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public TransactionDTO report(@RequestBody TransactionDTO dto) {
        log.info("Process transaction with details: {}", dto);
        return service.report(dto);
    }
}
