package com.ahmedatef.notificationservice.controller;

import com.ahmedatef.notificationservice.dto.TransactionDTO;
import com.ahmedatef.notificationservice.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fraudulent-transaction")
@RequiredArgsConstructor
@Slf4j
public class UserNotificationController {
    private final UserNotificationService service;

    @PostMapping
    public TransactionDTO notify(@RequestBody TransactionDTO dto) {
        log.info("Process transaction with details and notify user: {}", dto);
        return service.notify(dto);
    }
}
