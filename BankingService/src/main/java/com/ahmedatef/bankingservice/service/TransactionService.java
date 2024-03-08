package com.ahmedatef.bankingservice.service;

import com.ahmedatef.bankingservice.common.ControllerResponse;
import com.ahmedatef.bankingservice.common.Mapper;
import com.ahmedatef.bankingservice.dto.TransactionDTO;
import com.ahmedatef.bankingservice.entity.Transaction;
import com.ahmedatef.bankingservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;

    public ControllerResponse<TransactionDTO> findAll() {
        List<TransactionDTO> transactions = repository.findAll().stream().map(transaction -> Mapper.map(transaction, TransactionDTO.class)).toList();
        return ControllerResponse.of(transactions);
    }

    public ControllerResponse<TransactionDTO> findById(int id) {
        Optional<Transaction> optionalTransaction = repository.findById(id);
        return optionalTransaction.map(transaction -> ControllerResponse.of(List.of(Mapper.map(transaction, TransactionDTO.class))))
                .orElse(ControllerResponse.createExceptionResponse(String.format("Transaction with ID %d is not found.", id)));
    }

//    public ControllerResponse<TransactionDTO> process(TransactionDTO dto) {
//
//    }
}
