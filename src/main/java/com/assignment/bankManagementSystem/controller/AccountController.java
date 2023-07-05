package com.assignment.bankManagementSystem.controller;

import com.assignment.bankManagementSystem.entities.Accounts;

import com.assignment.bankManagementSystem.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;

    @PostMapping("/openAccount")
    public ResponseEntity<Accounts> openAccount(@RequestBody Accounts account){
        Accounts createdAccount=accountServices.openAccount(account);
        return ResponseEntity.ok(createdAccount);
    }
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Accounts> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        Accounts account =accountServices.getAccountByAccountNumber(accountNumber);
        if (account!=null){
            return ResponseEntity.ok(account);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("allAccounts/user/{userId}")
    public ResponseEntity<List<Accounts>> getAccountsByUserId (@PathVariable("userId") int userId)
    {
        List<Accounts> accountDetails=accountServices.getAccountsByUserId(userId);
        if(!accountDetails.isEmpty())
        {
            return ResponseEntity.ok(accountDetails);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<Accounts> deposit(@PathVariable String accountNumber,@RequestParam double depositAmount){
        Accounts account=accountServices.deposit(accountNumber,depositAmount);
        if (account!=null){
            return ResponseEntity.ok(account);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/withdraw/{accountNumber}")
    public ResponseEntity<Accounts> withdraw(@PathVariable String accountNumber,@RequestParam double withdrawAmount){
        Accounts account=accountServices.getAccountByAccountNumber(accountNumber);
        if (account!=null&&withdrawAmount<=account.getBalance()){
            account=accountServices.withdraw(accountNumber,withdrawAmount);
            return ResponseEntity.ok(account);
        }
        else{
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> balanceEnquiry(@PathVariable String accountNumber){
        Double balance=accountServices.balanceEnquiry(accountNumber);
        if (balance!=null){
            return ResponseEntity.ok(balance);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<HttpStatus> deleteAccount(String accountNumber){
        Accounts account=accountServices.getAccountByAccountNumber(accountNumber);
        if (account!=null){
            accountServices.deleteAccount(accountNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }

    }

}
