package com.ahmedatef.notificationservice.service;

import com.ahmedatef.notificationservice.common.ControllerResponse;
import com.ahmedatef.notificationservice.common.Mapper;
import com.ahmedatef.notificationservice.dto.TransactionDTO;
import com.ahmedatef.notificationservice.entity.Transaction;
import com.ahmedatef.notificationservice.entity.User;
import com.ahmedatef.notificationservice.enums.TransactionStatus;
import com.ahmedatef.notificationservice.repository.TransactionRepository;
import com.ahmedatef.notificationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNotificationService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public ControllerResponse<TransactionDTO> notify(TransactionDTO dto) {
        if (dto.getTransactionStatus().equals(TransactionStatus.FRAUDULENT)) {
            User user = userRepository.findUserByCardId(dto.getCardId());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Fraudulent transaction attempt from your card");
            message.setFrom("noreply@ahmedatef.com");
            message.setTo(user.getEmail());
            message.setText("An attempt has been made to pay " + dto.getStoreName() + " from your card " + dto.getCardId() + " in the country " + dto.getTransactionLocation() + ".");

            javaMailSender.send(message);
            dto.setTransactionStatus(TransactionStatus.FRAUDULENT_NOTIFY_SUCCESS);
        } else
            dto.setTransactionStatus(TransactionStatus.FRAUDULENT_NOTIFY_FAILURE);

        Transaction savedTransaction = transactionRepository.save(Mapper.map(dto, Transaction.class));
        return ControllerResponse.of(List.of(Mapper.map(savedTransaction, TransactionDTO.class)));
    }
}
