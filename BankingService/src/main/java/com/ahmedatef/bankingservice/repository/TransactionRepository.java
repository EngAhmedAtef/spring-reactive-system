package com.ahmedatef.bankingservice.repository;

import com.ahmedatef.bankingservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
