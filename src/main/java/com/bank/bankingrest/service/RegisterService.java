package com.bank.bankingrest.service;

import com.bank.bankingrest.DTO.RegisterRequest;
import com.bank.bankingrest.entity.User;
import com.bank.bankingrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public RegisterService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request){
        User user = new User();
        user.setId(0);
        user.setBalance(0);
        user.setName(request.getName());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(user);
    }
}
