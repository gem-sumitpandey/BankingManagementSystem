package com.assignment.bankManagementSystem.services;

import com.assignment.bankManagementSystem.dao.AccountDao;
import com.assignment.bankManagementSystem.dao.UserDao;
import com.assignment.bankManagementSystem.dto.AccountReadDto;
import com.assignment.bankManagementSystem.dto.AccountUpdateDto;
import com.assignment.bankManagementSystem.dto.AccountWriteDto;
import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.entities.Users;

import com.assignment.bankManagementSystem.exceptions.InsufficientBalanceException;
import com.assignment.bankManagementSystem.exceptions.InvalidArgumentException;
import com.assignment.bankManagementSystem.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
@Service
public class AccountServicesImplementation implements AccountServices {
 @Autowired
    AccountDao accountDao;
 @Autowired
     UserDao userDao;
    private AccountWriteDto convertToWriteDto(Accounts account) {
        AccountWriteDto dto= new AccountWriteDto();
        dto.setUserId((account.getUser().getUserId()));
        dto.setAccountType(account.getAccountType());
        dto.setBranch(account.getBranch());
        dto.setBalance(account.getBalance());

        return dto;
    }
    private AccountReadDto convertToReadDTO(Accounts accounts) {
        AccountReadDto accountReadDto=new AccountReadDto();
        accountReadDto.setAccountNumber(accounts.getAccountNumber());
        accountReadDto.setBalance(accounts.getBalance());
        return accountReadDto;
    }


    @Override
    public AccountWriteDto openAccount(AccountWriteDto accountWriteDto) {
        Users user = userDao.findById(accountWriteDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + accountWriteDto.getUserId()));
        Accounts account = new Accounts();
        account.setAccountType(accountWriteDto.getAccountType());
        account.setBranch(accountWriteDto.getBranch());
        account.setBalance(accountWriteDto.getBalance());
        account.setUser(user);

        account = accountDao.save(account);

        return convertToWriteDto(account);

    }
    public Accounts updateAccount(String accountNumber,AccountUpdateDto accountUpdateDto){
        Accounts account=accountDao.findById(accountNumber).orElseThrow(()->new ResourceNotFoundException("No account exists with account number: "+accountNumber));
        account.setAccountType(accountUpdateDto.getAccountType());
        account.setBranch(accountUpdateDto.getBranch());
        account=accountDao.save(account);
        return account;
    }

    @Override
    public Accounts getAccountByAccountNumber(String accountNumber) {
        Accounts account =  accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));

            return  account;



    }


    public AccountReadDto deposit(String accountNumber, double depositAmount){
        Accounts account=accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));
        if(depositAmount<=0){
            throw new InvalidArgumentException("Deposit Amount should be greater than 0");
        }
            account.setBalance(account.getBalance()+depositAmount);
             accountDao.save(account);
        return  convertToReadDTO(account);
        }

    public AccountReadDto withdraw(String accountNumber, double withdrawAmount)  {
        Accounts account=accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));

        if(withdrawAmount<=0){
            throw new InvalidArgumentException("Withdraw Amount should be greater than 0");
        }
            double currentBalance=account.getBalance();
            if (currentBalance<withdrawAmount){
                throw new InsufficientBalanceException("Insufficient Balance");}
            else{
            account.setBalance(account.getBalance()-withdrawAmount);
                accountDao.save(account);
        }
         return  convertToReadDTO(account);
    }




    public Double balanceEnquiry(String accountNumber){
        Accounts account=accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));

           return account.getBalance();
        }

    public List<Accounts> getAccountsByUserId(int userId) {
        Users user=userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user exist with userId: " + userId));
        return accountDao.findAllByUserUserId(userId);
    }

public void deleteAccount(String accountNumber){
        Accounts account=accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));

            accountDao.deleteById(accountNumber);
        }

}



