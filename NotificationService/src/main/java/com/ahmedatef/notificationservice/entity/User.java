package com.ahmedatef.notificationservice.entity;

import com.ahmedatef.notificationservice.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "home_country")
    private String homeCountry;
    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "card_id")
    private String cardId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "account_locked")
    private boolean accountLocked;
    @Column(name = "fraudulent_activity_attempt_count")
    @JsonIgnore
    private Long fraudulentActivityAttemptCount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Transaction> transactions;
}
