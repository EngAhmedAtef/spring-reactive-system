package com.ahmedatef.accountmanagementservice.repository;

import com.ahmedatef.accountmanagementservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
