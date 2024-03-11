package com.ahmedatef.bankingservice.service;

import com.ahmedatef.bankingservice.common.ControllerResponse;
import com.ahmedatef.bankingservice.common.Mapper;
import com.ahmedatef.bankingservice.dto.TransactionDTO;
import com.ahmedatef.bankingservice.entity.Transaction;
import com.ahmedatef.bankingservice.entity.User;
import com.ahmedatef.bankingservice.enums.TransactionStatus;
import com.ahmedatef.bankingservice.repository.TransactionRepository;
import com.ahmedatef.bankingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private static final String USER_NOTIFICATION_SERVICE_URL = "http://localhost:8081/notification/fraudulent-transaction";
    private static final String REPORTING_SERVICE_URL = "http://localhost:8082/reporting/report";
    private static final String ACCOUNT_MANAGER_SERVICE_URL = "http://localhost:8083/account-management/process";

    private final TransactionRepository repository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public ControllerResponse<TransactionDTO> findAll() {
        List<TransactionDTO> transactions = repository.findAll().stream().map(transaction -> Mapper.map(transaction, TransactionDTO.class)).toList();
        return ControllerResponse.of(transactions);
    }

    public ControllerResponse<TransactionDTO> findById(int id) {
        Optional<Transaction> optionalTransaction = repository.findById(id);
        return optionalTransaction.map(transaction -> ControllerResponse.of(List.of(Mapper.map(transaction, TransactionDTO.class))))
                .orElse(ControllerResponse.createExceptionResponse(String.format("Transaction with ID %d is not found.", id)));
    }

    public ControllerResponse<TransactionDTO> process(TransactionDTO dto) {
        TransactionDTO firstProcessed;
        TransactionDTO secondProcessed = null;

        if (dto.getTransactionStatus().equals(TransactionStatus.INITIATED)) {
            User user = userRepository.findUserByCardId(dto.getCardId());

            if (user == null)
                dto.setTransactionStatus(TransactionStatus.CARD_INVALID);
            else if (user.isAccountLocked())
                dto.setTransactionStatus(TransactionStatus.ACCOUNT_BLOCKED);
            else {
                if (user.getHomeCountry().equals(dto.getTransactionLocation())) {
                    dto.setTransactionStatus(TransactionStatus.VALID);

                    // Call Reporting Service to report valid transaction to bank
                    // and deduct amount if funds available
                    firstProcessed = restTemplate.postForObject(REPORTING_SERVICE_URL, dto, TransactionDTO.class);

                    // Call Account Manager service to process the transaction
                    // and send the money
                    if (firstProcessed != null)
                        secondProcessed = restTemplate.postForObject(ACCOUNT_MANAGER_SERVICE_URL, firstProcessed, TransactionDTO.class);

                    if (secondProcessed != null)
                        dto = secondProcessed;
                } else {
                    dto.setTransactionStatus(TransactionStatus.FRAUDULENT);

                    // Call User Notification service to notify for a
                    // fraudulent transaction attempt from the User's card
                    firstProcessed = restTemplate.postForObject(USER_NOTIFICATION_SERVICE_URL, dto, TransactionDTO.class);

                    // Call Reporting Service to notify bank that
                    // there has been an attempt for fraudulent transaction
                    // and if this attempt exceeds 3 times then auto-block
                    // the card and account
                    if (firstProcessed != null)
                        secondProcessed = restTemplate.postForObject(REPORTING_SERVICE_URL, firstProcessed, TransactionDTO.class);

                    if (secondProcessed != null)
                        dto = secondProcessed;
                }
            }

        } else
            dto.setTransactionStatus(TransactionStatus.FAILURE);

        Transaction savedTransaction = repository.save(Mapper.map(dto, Transaction.class));
        return ControllerResponse.of(List.of(Mapper.map(savedTransaction, TransactionDTO.class)));
    }
}
