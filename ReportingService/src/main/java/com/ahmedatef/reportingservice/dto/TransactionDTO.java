package com.ahmedatef.reportingservice.dto;

import com.ahmedatef.reportingservice.enums.TransactionStatus;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
