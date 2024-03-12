package com.ahmedatef.accountmanagementservice.dto;

import com.ahmedatef.accountmanagementservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String homeCountry;
    private Gender gender;
    private String mobile;
    private String accountNumber;
    private String accountType;
    private boolean accountLocked;
}
