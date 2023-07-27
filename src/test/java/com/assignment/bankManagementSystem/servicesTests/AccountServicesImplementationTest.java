package com.assignment.bankManagementSystem.servicesTests;

import com.assignment.bankManagementSystem.dao.AccountDao;
import com.assignment.bankManagementSystem.dao.UserDao;
import com.assignment.bankManagementSystem.dto.AccountReadDto;
import com.assignment.bankManagementSystem.dto.AccountUpdateDto;
import com.assignment.bankManagementSystem.dto.AccountWriteDto;
import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.entities.Users;
import com.assignment.bankManagementSystem.exceptions.InsufficientBalanceException;
import com.assignment.bankManagementSystem.exceptions.ResourceNotFoundException;
import com.assignment.bankManagementSystem.services.AccountServicesImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServicesImplementationTest {

    @Mock
    private AccountDao accountDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private AccountServicesImplementation accountServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOpenAccount() {

        int userId = 1;
        AccountWriteDto accountWriteDto = new AccountWriteDto();
        accountWriteDto.setUserId(userId);
        accountWriteDto.setAccountType("Savings");
        accountWriteDto.setBranch("Main Branch");
        accountWriteDto.setBalance(1000.0);

        Users user = new Users();
        user.setUserId(userId);

        Accounts account = new Accounts();
        account.setAccountType(accountWriteDto.getAccountType());
        account.setBranch(accountWriteDto.getBranch());
        account.setBalance(accountWriteDto.getBalance());
        account.setUser(user);

        when(userDao.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(accountDao.save(any(Accounts.class))).thenReturn(account);

        AccountWriteDto resultDto = accountServices.openAccount(accountWriteDto);

        assertNotNull(resultDto);
        assertEquals(userId, resultDto.getUserId());
        assertEquals(accountWriteDto.getAccountType(), resultDto.getAccountType());
        assertEquals(accountWriteDto.getBranch(), resultDto.getBranch());
        assertEquals(accountWriteDto.getBalance(), resultDto.getBalance());
    }

    @Test
    public void testDeposit() {

        String accountNumber = "ACC123";
        double currentBalance = 1000.0;
        double depositAmount = 500.0;

        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);
        account.setBalance(currentBalance);

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.of(account));

        AccountReadDto resultDto = accountServices.deposit(accountNumber, depositAmount);

        assertNotNull(resultDto);
        assertEquals(currentBalance + depositAmount, resultDto.getBalance());
    }



    @Test
    public void testWithdrawSufficientBalance() {

        String accountNumber = "ACC123";
        double currentBalance = 1000.0;
        double withdrawAmount = 500.0;

        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);
        account.setBalance(currentBalance);

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.of(account));

        AccountReadDto resultDto = accountServices.withdraw(accountNumber, withdrawAmount);

        assertNotNull(resultDto);
        assertEquals(currentBalance - withdrawAmount, resultDto.getBalance());
    }

    @Test
    public void testWithdrawInsufficientBalance() {

        String accountNumber = "ACC123";
        double currentBalance = 1000.0;
        double withdrawAmount = 1500.0;

        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);
        account.setBalance(currentBalance);

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.of(account));

        assertThrows(InsufficientBalanceException.class, () -> accountServices.withdraw(accountNumber, withdrawAmount));
    }
    @Test
    void updateAccount_existingAccountNumberAndValidAccountUpdateDto_shouldReturnUpdatedAccount() {
        String accountNumber = "123456789";
        Accounts existingAccount = createAccount(accountNumber, "Savings", "Main Branch");
        AccountUpdateDto accountUpdateDto = createAccountUpdateDto("Current", "New Branch");
        when(accountDao.findById(accountNumber)).thenReturn(Optional.of(existingAccount));
        when(accountDao.save(any())).thenReturn(existingAccount);


        Accounts result = accountServices.updateAccount(accountNumber, accountUpdateDto);


        assertNotNull(result);
        assertEquals(accountUpdateDto.getAccountType(), result.getAccountType());
        assertEquals(accountUpdateDto.getBranch(), result.getBranch());

    }

    @Test
    void updateAccount_nonExistingAccountNumber_shouldThrowException() {

        String accountNumber = "987654321";
        AccountUpdateDto accountUpdateDto = createAccountUpdateDto("Current", "New Branch");
        when(accountDao.findById(accountNumber)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountServices.updateAccount(accountNumber, accountUpdateDto));
    }




    @Test
    public void testBalanceEnquiry() {

        String accountNumber = "ACC123";
        double currentBalance = 1000.0;

        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);
        account.setBalance(currentBalance);

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.of(account));

        Double resultBalance = accountServices.balanceEnquiry(accountNumber);

        assertNotNull(resultBalance);
        assertEquals(currentBalance, resultBalance);
    }

    @Test
    public void testGetAccountByAccountNumberExists() {
        String accountNumber = "ACC123";

        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.of(account));

        Accounts resultAccount = accountServices.getAccountByAccountNumber(accountNumber);

        assertNotNull(resultAccount);
        assertEquals(accountNumber, resultAccount.getAccountNumber());
    }

    @Test
    public void testGetAccountByAccountNumberNotExists() {

        String accountNumber = "NON_EXISTENT_ACC";

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountServices.getAccountByAccountNumber(accountNumber));
    }

    @Test
    public void testGetAccountsByUserIdExists() {
        int userId = 1;
        Users user = new Users();
        user.setUserId(userId);

        Accounts account1 = new Accounts();
        account1.setAccountNumber("ACC123");
        account1.setUser(user);

        Accounts account2 = new Accounts();
        account2.setAccountNumber("ACC456");
        account2.setUser(user);

        when(userDao.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(accountDao.findAllByUserUserId(userId)).thenReturn(List.of(account1, account2));

        List<Accounts> resultAccounts = accountServices.getAccountsByUserId(userId);

        assertNotNull(resultAccounts);
        assertEquals(2, resultAccounts.size());
    }

    @Test
    public void testGetAccountsByUserIdNotExists() {

        int userId = 100;

        when(userDao.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountServices.getAccountsByUserId(userId));
    }

    @Test
    public void testDeleteAccountExists() {

        String accountNumber = "ACC123";

        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.of(account));

        accountServices.deleteAccount(accountNumber);

        verify(accountDao, times(1)).deleteById(accountNumber);
    }

    @Test
    public void testDeleteAccountNotExists() {

        String accountNumber = "NON_EXISTENT_ACC";

        when(accountDao.findById(accountNumber)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountServices.deleteAccount(accountNumber));
    }




    private Accounts createAccount(String accountNumber, String accountType, String branch) {
        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setBranch(branch);

        return account;
    }


    private AccountUpdateDto createAccountUpdateDto(String accountType, String branch) {
        AccountUpdateDto accountUpdateDto = new AccountUpdateDto();
        accountUpdateDto.setAccountType(accountType);
        accountUpdateDto.setBranch(branch);
        return accountUpdateDto;
    }


}
