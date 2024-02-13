package com.bank.bankingrest.controller;

import com.bank.bankingrest.DTO.TransactionRequest;
import com.bank.bankingrest.entity.Transaction;
import com.bank.bankingrest.security.UserDetailsImpl;
import com.bank.bankingrest.service.TransactionService;
import com.bank.bankingrest.service.UserFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class BankController {
    private final TransactionService transactionService;
    private final UserFindService userFindService;
    @Autowired
    public BankController(TransactionService transactionService, UserFindService userFindService) {
        this.transactionService = transactionService;
        this.userFindService = userFindService;
    }

    @GetMapping("/transactions")
    public List<Transaction> listOfTransaction(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return transactionService.getAllTransaction(userDetails.getUser().getId());
    }
    @PostMapping("/topup")
    public ResponseEntity<?> topUp(Authentication authentication, @RequestParam("sum") int sum){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        transactionService.topUp(sum,userDetails.getUser());
        return ResponseEntity.ok().body("Balance for " + userDetails.getUser().getLogin() + " is replenished by " + sum + "$");
    }
    @PostMapping("/transaction/new")
    public ResponseEntity<?> newTransaction(Authentication authentication, @RequestBody TransactionRequest request){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getUser().getBalance() >= request.getAmount()){
            transactionService.newTransaction(request,userDetails.getUser(),userFindService.loadUserByLogin(request.getRecipient()));
            return ResponseEntity.ok().body("Balance is updated!");
        }
        else return ResponseEntity.badRequest().body("Insufficient funds!");
    }


}
