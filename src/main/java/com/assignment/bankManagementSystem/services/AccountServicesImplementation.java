package com.assignment.bankManagementSystem.services;

import com.assignment.bankManagementSystem.dao.AccountDao;
import com.assignment.bankManagementSystem.entities.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServicesImplementation implements AccountServices {
 @Autowired
    AccountDao accountDao;


    @Override
    public Accounts openAccount(Accounts account) {

         accountDao.save(account);
         return account;

    }

    @Override
    public Accounts getAccountByAccountNumber(String accountNumber) {
        Accounts account =  accountDao.findById(accountNumber).orElse(null);

            return  account;



    }


    public Accounts deposit(String accountNumber,double depositAmount){
        Accounts account=accountDao.findById(accountNumber).orElse(null);
        if (account!=null){
            account.setBalance(account.getBalance()+depositAmount);
            return accountDao.save(account);
        }
        else{
            return null;
        }
    }
    public Accounts withdraw(String accountNumber,double withdrawAmount)  {
        Accounts account=accountDao.findById(accountNumber).orElse(null);
        if (account!=null){

            double currentBalance=account.getBalance();
            if(currentBalance>=withdrawAmount){
                account.setBalance(currentBalance-withdrawAmount);
            }

            return accountDao.save(account);
        }
        else{
            return null;
        }
    }



    public Double balanceEnquiry(String accountNumber){
        Accounts account=accountDao.findById(accountNumber).orElse(null);
        if (account!=null){
           return account.getBalance();
        }
        else{
            return null;
        }
    }
    public List<Accounts> getAccountsByUserId(int userId) {
        return accountDao.findAllByUserUserId(userId);
    }

public void deleteAccount(String accountNumber){
        Accounts account=accountDao.findById(accountNumber).orElse(null);
        if (account!=null){
            accountDao.deleteById(accountNumber);
        }

}


}
