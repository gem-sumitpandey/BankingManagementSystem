package com.assignment.bankManagementSystem.controller;

import com.assignment.bankManagementSystem.entities.Accounts;

import com.assignment.bankManagementSystem.services.AccountServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;
    private Logger logger= LoggerFactory.getLogger(AccountController.class);
    @PostMapping("/openAccount")
    public ResponseEntity<Accounts> openAccount(@RequestBody Accounts account){
        logger.info("Creating account: {}",account );
        Accounts createdAccount=accountServices.openAccount(account);
        return ResponseEntity.ok(createdAccount);
    }
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Accounts> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        logger.info("Fetching Account By Given Account number: {}",accountNumber );
        Accounts account =accountServices.getAccountByAccountNumber(accountNumber);
        if (account!=null){
            logger.info("Account found");
            return ResponseEntity.ok(account);
        }
        else{
            logger.warn("Account not found");
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("allAccounts/user/{userId}")
    public ResponseEntity<List<Accounts>> getAccountsByUserId (@PathVariable("userId") int userId)
    {
        logger.info("Fetching all accounts that are associated to a single user by userId: {}",userId );
        List<Accounts> accountDetails=accountServices.getAccountsByUserId(userId);
        if(!accountDetails.isEmpty())
        {   logger.info("All accounts found");
            return ResponseEntity.ok(accountDetails);
        }
        else {
            logger.warn("No account found");
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<Accounts> deposit(@PathVariable String accountNumber,@RequestParam double depositAmount){

        logger.info("Depositing amount {} into account number {}",depositAmount,accountNumber);
        Accounts account=accountServices.deposit(accountNumber,depositAmount);
        if (account!=null){
            logger.info("Amount deposited");
            return ResponseEntity.ok(account);
        }
        else{
            logger.warn("Account not found");
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/withdraw/{accountNumber}")
    public ResponseEntity<Accounts> withdraw(@PathVariable String accountNumber,@RequestParam double withdrawAmount){
        logger.info("Withdrawing amount {} from account number {}",withdrawAmount,accountNumber);
        Accounts account=accountServices.getAccountByAccountNumber(accountNumber);
        if (account!=null&&withdrawAmount<=account.getBalance()){
            account=accountServices.withdraw(accountNumber,withdrawAmount);
            logger.info("Amount withdraw success");
            return ResponseEntity.ok(account);
        }
        else{
            logger.warn("Amount withdraw failed");
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> balanceEnquiry(@PathVariable String accountNumber){
        logger.info("Fetching balance of account number {}",accountNumber);
        Double balance=accountServices.balanceEnquiry(accountNumber);
        if (balance!=null){
            logger.info("Balance {}",balance);
            return ResponseEntity.ok(balance);
        }
        else{
            logger.warn("Account not found");
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<HttpStatus> deleteAccount(String accountNumber){
        logger.info("Deleting account with account number {}",accountNumber);
        Accounts account=accountServices.getAccountByAccountNumber(accountNumber);
        if (account!=null){
            accountServices.deleteAccount(accountNumber);
            logger.info("Account deleted successfully");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            logger.warn("Account does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }

    }
}
