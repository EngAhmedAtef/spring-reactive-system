package com.ahmedatef.notificationservice.controller;

import com.ahmedatef.notificationservice.common.ControllerResponse;
import com.ahmedatef.notificationservice.dto.TransactionDTO;
import com.ahmedatef.notificationservice.enums.TransactionStatus;
import com.ahmedatef.notificationservice.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ControllerResponse<TransactionDTO>> notify(@RequestBody TransactionDTO dto) {
        log.info("Process transaction with details and notify user: {}", dto);
        ControllerResponse<TransactionDTO> processed = service.notify(dto);
        if (processed.getData().get(0).getTransactionStatus().equals(TransactionStatus.SUCCESS))
            return new ResponseEntity<>(processed, HttpStatus.OK);
        else
            return ResponseEntity.internalServerError().body(processed);
    }
}
