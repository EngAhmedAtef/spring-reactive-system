package com.ahmedatef.reportingservice.repository;

import com.ahmedatef.reportingservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
