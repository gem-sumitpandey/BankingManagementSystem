package com.assignment.bankManagementSystem.services;

import com.assignment.bankManagementSystem.dao.AccountDao;
import com.assignment.bankManagementSystem.dao.UserDao;
import com.assignment.bankManagementSystem.dto.AccountWriteDto;
import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.entities.Users;
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
    private AccountWriteDto convertToDTO(Accounts account) {
        AccountWriteDto dto= new AccountWriteDto();
        dto.setUserId((account.getUser().getUserId()));
        dto.setAccountType(account.getAccountType());
        dto.setBranch(account.getBranch());
        dto.setBalance(account.getBalance());

        return dto;
    }


    @Override
    public AccountWriteDto openAccount(@Valid AccountWriteDto accountWriteDto) {
        Users user = userDao.findById(accountWriteDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + accountWriteDto.getUserId()));
        Accounts account = new Accounts();
        account.setAccountType(accountWriteDto.getAccountType());
        account.setBranch(accountWriteDto.getBranch());
        account.setBalance(accountWriteDto.getBalance());
        account.setUser(user);

        account = accountDao.save(account);

        return convertToDTO(account);

    }

    @Override
    public Accounts getAccountByAccountNumber(String accountNumber) {
        Accounts account =  accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));

            return  account;



    }


    public Accounts deposit(String accountNumber,double depositAmount){
        Accounts account=accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));

            account.setBalance(account.getBalance()+depositAmount);
            return accountDao.save(account);
        }

    public Accounts withdraw(String accountNumber,double withdrawAmount)  {
        Accounts account=accountDao.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No account exist with account number: " + accountNumber));


            double currentBalance=account.getBalance();
            if(currentBalance>=withdrawAmount){
                account.setBalance(currentBalance-withdrawAmount);
            }

            return accountDao.save(account);
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



