package com.ahmedatef.bankingservice.controller;

import com.ahmedatef.bankingservice.common.ControllerResponse;
import com.ahmedatef.bankingservice.dto.UserDTO;
import com.ahmedatef.bankingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<ControllerResponse<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ControllerResponse<UserDTO>> getUserById(@PathVariable int id) {
        ControllerResponse<UserDTO> response = service.findById(id);
        HttpStatus status = response.getMessage() == null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }
}
