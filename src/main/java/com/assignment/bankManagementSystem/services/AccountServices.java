package com.assignment.bankManagementSystem.services;

import com.assignment.bankManagementSystem.dto.AccountReadDto;
import com.assignment.bankManagementSystem.dto.AccountUpdateDto;
import com.assignment.bankManagementSystem.dto.AccountWriteDto;
import com.assignment.bankManagementSystem.entities.Accounts;

import javax.validation.Valid;
import java.util.List;

public interface AccountServices {



    AccountWriteDto openAccount(@Valid AccountWriteDto accountWriteDto);

    public Accounts getAccountByAccountNumber(String accountNumber);
    public AccountReadDto deposit(String accountID, double depositAmount);
    public AccountReadDto withdraw(String accountID, double withdrawAmount);
    public Double balanceEnquiry(String accountID);
    public void deleteAccount(String accountID);

    List<Accounts> getAccountsByUserId(int userId);

    public Accounts updateAccount(String accountNumber,@Valid AccountUpdateDto accountUpdateDto);
}
