package com.ahmedatef.notificationservice.service;

import com.ahmedatef.notificationservice.common.Mapper;
import com.ahmedatef.notificationservice.dto.TransactionDTO;
import com.ahmedatef.notificationservice.entity.Transaction;
import com.ahmedatef.notificationservice.entity.User;
import com.ahmedatef.notificationservice.enums.TransactionStatus;
import com.ahmedatef.notificationservice.repository.TransactionRepository;
import com.ahmedatef.notificationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserNotificationService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
//    private final JavaMailSender javaMailSender = new JavaMailSenderImpl();

    public TransactionDTO notify(TransactionDTO dto) {
        Transaction transaction = Mapper.map(dto, Transaction.class);

        if (dto.getTransactionStatus().equals(TransactionStatus.FRAUDULENT)) {
            User user = userRepository.findUserByCardId(dto.getCardId());

//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setSubject("Fraudulent transaction attempt from your card");
//            message.setFrom("noreply@ahmedatef.com");
//            message.setTo(user.getEmail());
//            message.setText("An attempt has been made to pay " + dto.getStoreName() + " from your card " + dto.getCardId() + " in the country " + dto.getTransactionLocation() + ".");

            log.info("An attempt has been made to pay " + dto.getStoreName() + " from your card " + dto.getCardId() + " in the country " + dto.getTransactionLocation() + ".");
//            javaMailSender.send(message);
            transaction.setTransactionStatus(TransactionStatus.FRAUDULENT_NOTIFY_SUCCESS);
            transaction.setUser(user);
        } else
            dto.setTransactionStatus(TransactionStatus.FRAUDULENT_NOTIFY_FAILURE);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return Mapper.map(savedTransaction, TransactionDTO.class);
    }
}
