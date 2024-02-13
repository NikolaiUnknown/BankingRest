package com.bank.bankingrest.service;

import com.bank.bankingrest.DTO.TransactionRequest;
import com.bank.bankingrest.entity.Transaction;
import com.bank.bankingrest.entity.User;
import com.bank.bankingrest.repository.TransactionRepository;
import com.bank.bankingrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository repository;
    private final UserRepository userRepository;
    @Autowired
    public TransactionService(TransactionRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }
    public List<Transaction> getAllTransaction(int id){
        List<Transaction> all = new ArrayList<>();
        all.addAll(repository.findAllByTransfer(id));
        all.addAll(repository.findAllByRecipient(id));
        return all;
    }
    public void newTransaction(TransactionRequest transactionDTO, User transfer, User recipient){
        Transaction transaction = new Transaction();
        transaction.setId(0);
        transaction.setTransfer(transfer.getId());
        transaction.setDate(new Date());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setRecipient(recipient.getId());
        userRepository.setAmountForUser(transfer.getBalance()-transactionDTO.getAmount(),transfer.getId());
        userRepository.setAmountForUser(recipient.getBalance()+transactionDTO.getAmount(),recipient.getId());
        repository.save(transaction);
    }
    public void topUp(int sum, User user){
        userRepository.setAmountForUser(user.getBalance() + sum,user.getId());
    }

}
