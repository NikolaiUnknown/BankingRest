package com.bank.bankingrest.service;

import com.bank.bankingrest.entity.User;
import com.bank.bankingrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserFindService {
    private final UserRepository repository;
    @Autowired
    public UserFindService(UserRepository repository) {
        this.repository = repository;
    }

    public User loadUserByLogin(String login){
        return repository.findUserByLogin(login);
    }
}
