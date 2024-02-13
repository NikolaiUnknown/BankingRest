package com.bank.bankingrest.repository;

import com.bank.bankingrest.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    List<Transaction> findAllByTransfer(int id);
    List<Transaction> findAllByRecipient(int id);

}
