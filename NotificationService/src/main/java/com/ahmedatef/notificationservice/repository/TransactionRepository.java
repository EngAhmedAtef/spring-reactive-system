package com.ahmedatef.notificationservice.repository;

import com.ahmedatef.notificationservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
