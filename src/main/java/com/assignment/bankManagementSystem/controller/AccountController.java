package com.assignment.bankManagementSystem.controller;

import com.assignment.bankManagementSystem.dto.AccountReadDto;
import com.assignment.bankManagementSystem.dto.AccountUpdateDto;
import com.assignment.bankManagementSystem.dto.AccountWriteDto;
import com.assignment.bankManagementSystem.entities.Accounts;

import com.assignment.bankManagementSystem.services.AccountServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;
    private Logger logger= LoggerFactory.getLogger(AccountController.class);
    @PostMapping("/openAccount")
    public ResponseEntity<AccountWriteDto> openAccount( @RequestBody @Valid AccountWriteDto account){
        logger.info("Creating account: {}",account );
        AccountWriteDto createdAccount=accountServices.openAccount(account);
        return ResponseEntity.ok(createdAccount);
    }
    @PostMapping("/updateAccount")
    public ResponseEntity<Accounts> updateAccount(@RequestParam String accountNumber, @RequestBody @Valid AccountUpdateDto account){
        logger.info("Updating details: {}",account);
        Accounts updatedAccount=accountServices.updateAccount(accountNumber,account);
        return ResponseEntity.ok(updatedAccount);
    }
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Accounts> getAccountByAccountNumber( @PathVariable("accountNumber") String accountNumber){
        logger.info("Fetching Account By Given Account number: {}",accountNumber );
        Accounts account =accountServices.getAccountByAccountNumber(accountNumber);

            logger.info("Account found");
            return ResponseEntity.ok(account);
        }

    @GetMapping("allAccounts/user/{userId}")
    public ResponseEntity<List<Accounts>> getAccountsByUserId ( @PathVariable("userId")int userId)
    {
        logger.info("Fetching all accounts that are associated to a single user by userId: {}",userId );
        List<Accounts> accountDetails=accountServices.getAccountsByUserId(userId);
            logger.info("All accounts found");
            return ResponseEntity.ok(accountDetails);


        }
    @PostMapping("/deposit/{accountNumber}")
    public ResponseEntity<AccountReadDto> deposit(@PathVariable String accountNumber,@RequestParam  double depositAmount){

        logger.info("Depositing amount {} into account number {}",depositAmount,accountNumber);
        AccountReadDto account=accountServices.deposit(accountNumber,depositAmount);

            logger.info("Amount deposited");
            return ResponseEntity.ok(account);


    }
    @PostMapping("/withdraw/{accountNumber}")
    public ResponseEntity<AccountReadDto> withdraw(@PathVariable String accountNumber,@RequestParam double withdrawAmount){
        logger.info("Withdrawing amount {} from account number {}",withdrawAmount,accountNumber);
        AccountReadDto account=accountServices.withdraw(accountNumber,withdrawAmount);
        return ResponseEntity.ok(account);

    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> balanceEnquiry(@PathVariable String accountNumber){
        logger.info("Fetching balance of account number {}",accountNumber);
        Double balance=accountServices.balanceEnquiry(accountNumber);
            logger.info("Balance {}",balance);
            return ResponseEntity.ok(balance);


    }
    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<HttpStatus> deleteAccount(String accountNumber){
        logger.info("Deleting account with account number {}",accountNumber);
            accountServices.deleteAccount(accountNumber);
            logger.info("Account deleted successfully");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

