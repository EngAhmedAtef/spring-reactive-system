package com.ahmedatef.bankingservice.service;

import com.ahmedatef.bankingservice.common.ControllerResponse;
import com.ahmedatef.bankingservice.common.Mapper;
import com.ahmedatef.bankingservice.dto.UserDTO;
import com.ahmedatef.bankingservice.entity.User;
import com.ahmedatef.bankingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    public ControllerResponse<UserDTO> findById(int id) {
        Optional<User> optionalUser = repository.findById(id);
        return optionalUser.map(user -> ControllerResponse.of(List.of(Mapper.map(optionalUser.get(), UserDTO.class))))
                .orElse(ControllerResponse.createExceptionResponse(String.format("User with ID %d is not found.", id)));

    }

    public ControllerResponse<UserDTO> findAll() {
        List<UserDTO> users = repository.findAll().stream().map(user -> Mapper.map(user, UserDTO.class)).toList();
        return ControllerResponse.of(users);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public User update(User user) {
        Optional<User> optional = repository.findById(user.getUserId());
        if (optional.isEmpty())
            return user;

        User dbUser = optional.get();

        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setEmail(user.getEmail());
        dbUser.setAddress(user.getAddress());
        dbUser.setHomeCountry(user.getHomeCountry());
        dbUser.setGender(user.getGender());
        dbUser.setMobile(user.getMobile());
        dbUser.setAccountNumber(user.getAccountNumber());
        dbUser.setAccountType(user.getAccountType());
        dbUser.setAccountLocked(user.isAccountLocked());
        dbUser.setFraudulentActivityAttemptCount(user.getFraudulentActivityAttemptCount());

        return repository.save(dbUser);
    }

}
