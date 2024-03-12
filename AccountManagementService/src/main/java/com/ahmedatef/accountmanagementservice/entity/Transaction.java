package com.ahmedatef.accountmanagementservice.entity;

import com.ahmedatef.accountmanagementservice.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    private int transactionId;
    @Column(name = "transaction_date")
    private Date transactionDate;
    @Column(name = "amount_deducted")
    private Long amountDeducted;
    @Column(name = "store_name")
    private String storeName;
    @Column(name = "store_id")
    private String storeId;
    @Column(name = "card_id")
    private String cardId;
    @Column(name = "transaction_location")
    private String transactionLocation;
    @Column(name = "transaction_status")
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
