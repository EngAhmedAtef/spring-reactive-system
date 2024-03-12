package com.ahmedatef.accountmanagementservice.service;

import com.ahmedatef.accountmanagementservice.common.Mapper;
import com.ahmedatef.accountmanagementservice.dto.TransactionDTO;
import com.ahmedatef.accountmanagementservice.entity.Transaction;
import com.ahmedatef.accountmanagementservice.entity.User;
import com.ahmedatef.accountmanagementservice.enums.TransactionStatus;
import com.ahmedatef.accountmanagementservice.repository.TransactionRepository;
import com.ahmedatef.accountmanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManagementService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionDTO manage(TransactionDTO dto) {
        Transaction transaction = Mapper.map(dto, Transaction.class);

        if (transaction.getTransactionStatus().equals(TransactionStatus.VALID)) {
            User user = userRepository.findUserByCardId(transaction.getCardId());
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transaction.setUser(user);
            transaction = transactionRepository.save(transaction);
        }

        return Mapper.map(transaction, TransactionDTO.class);
    }
}
