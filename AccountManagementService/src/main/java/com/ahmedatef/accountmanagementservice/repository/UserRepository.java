package com.ahmedatef.accountmanagementservice.repository;

import com.ahmedatef.accountmanagementservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByCardId(String cardId);
}
