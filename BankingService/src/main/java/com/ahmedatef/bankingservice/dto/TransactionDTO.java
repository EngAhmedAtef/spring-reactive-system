package com.ahmedatef.bankingservice.dto;

import com.ahmedatef.bankingservice.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private int transactionId;
    private Date transactionDate;
    private Long amountDeducted;
    private String storeName;
    private String storeId;
    private String cardId;
    private String transactionLocation;
    private TransactionStatus transactionStatus;
}
