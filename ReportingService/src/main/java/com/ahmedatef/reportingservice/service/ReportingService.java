package com.ahmedatef.reportingservice.service;

import com.ahmedatef.reportingservice.common.Mapper;
import com.ahmedatef.reportingservice.dto.TransactionDTO;
import com.ahmedatef.reportingservice.entity.Transaction;
import com.ahmedatef.reportingservice.entity.User;
import com.ahmedatef.reportingservice.enums.TransactionStatus;
import com.ahmedatef.reportingservice.repository.TransactionRepository;
import com.ahmedatef.reportingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionDTO report(TransactionDTO dto) {
        Transaction transaction = Mapper.map(dto, Transaction.class);

        if (dto.getTransactionStatus().equals(TransactionStatus.FRAUDULENT_NOTIFY_SUCCESS) ||
                dto.getTransactionStatus().equals(TransactionStatus.FRAUDULENT_NOTIFY_FAILURE)) {

            User user = userRepository.findUserByCardId(dto.getCardId());
            user.setFraudulentActivityAttemptCount(user.getFraudulentActivityAttemptCount() + 1);
            user.setAccountLocked(user.getFraudulentActivityAttemptCount() > 3);
            transaction.setUser(user);
            userRepository.save(user);

            transaction.setTransactionStatus(user.isAccountLocked() ? TransactionStatus.ACCOUNT_BLOCKED : TransactionStatus.FAILURE);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return Mapper.map(savedTransaction, TransactionDTO.class);
    }
}
