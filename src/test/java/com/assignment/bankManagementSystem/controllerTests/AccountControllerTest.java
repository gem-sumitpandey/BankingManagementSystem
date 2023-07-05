package com.assignment.bankManagementSystem.controllerTests;

import com.assignment.bankManagementSystem.controller.AccountController;
import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.services.AccountServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AccountControllerTest {
    @Mock
    private AccountServices accountServices;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenAccount() {

        Accounts account = new Accounts();
        account.setAccountNumber("123456789");
        when(accountServices.openAccount(any(Accounts.class))).thenReturn(account);

        ResponseEntity<Accounts> response = accountController.openAccount(account);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123456789", response.getBody().getAccountNumber());
        verify(accountServices, times(1)).openAccount(any(Accounts.class));
    }


    @Test
    public void testDeposit() throws Exception {
        String accountNumber = "123456789";
        double depositAmount = 1000.0;
        Accounts account = new Accounts();
        account.setAccountNumber(accountNumber);
        account.setBalance(500.00);

        when(accountServices.deposit(eq("123456789"),anyDouble())).thenReturn(account);

        ResponseEntity<Accounts> response = accountController.deposit(accountNumber, depositAmount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());

   verify(accountServices,times(1)).deposit(eq("123456789"),eq(1000.0d));
    }




    @Test
    void testWithdraw_ValidAccountNumberSufficientBalance(){
        // Arrange
        String validAccountNumber = "123456789";
        double withdrawAmount = 100.0;
        Accounts mockedAccount = new Accounts();
        mockedAccount.setAccountNumber(validAccountNumber);
        mockedAccount.setBalance(200.0);
        when(accountServices.getAccountByAccountNumber(validAccountNumber)).thenReturn(mockedAccount);
        when(accountServices.withdraw(validAccountNumber, withdrawAmount)).thenReturn(mockedAccount);

        ResponseEntity<Accounts> response = accountController.withdraw(validAccountNumber, withdrawAmount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedAccount, response.getBody());
        verify(accountServices, times(1)).getAccountByAccountNumber(validAccountNumber);
        verify(accountServices, times(1)).withdraw(validAccountNumber, withdrawAmount);
    }
    @Test
    void testWithdraw_InvalidAccountNumber() {
        // Arrange
        String invalidAccountNumber = "123456789";
        double withdrawAmount = 100.0;
        when(accountServices.getAccountByAccountNumber(invalidAccountNumber)).thenReturn(null);


        ResponseEntity<Accounts> response = accountController.withdraw(invalidAccountNumber, withdrawAmount);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(accountServices, times(1)).getAccountByAccountNumber(invalidAccountNumber);
        verify(accountServices, never()).withdraw(anyString(), anyDouble());
    }

    @Test
    void testWithdraw_InsufficientBalance() {

        String validAccountNumber = "123456789";
        double withdrawAmount = 1000.0;
        Accounts mockedAccount = new Accounts();
        mockedAccount.setAccountNumber(validAccountNumber);
        mockedAccount.setBalance(500.0);
        when(accountServices.getAccountByAccountNumber(validAccountNumber)).thenReturn(mockedAccount);


        ResponseEntity<Accounts> response = accountController.withdraw(validAccountNumber, withdrawAmount);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(accountServices, times(1)).getAccountByAccountNumber(validAccountNumber);
        verify(accountServices, never()).withdraw(anyString(), anyDouble());
    }

    @Test
    void testBalanceEnquiry() {
        Double balance = 100.0;

        when(accountServices.balanceEnquiry(eq("123456789"))).thenReturn(balance);


        ResponseEntity<Double> response = accountController.balanceEnquiry("123456789");


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100.0, response.getBody());

        verify(accountServices, times(1)).balanceEnquiry(eq("123456789"));
    }

    @Test
    void testDeleteAccount() {

        Accounts account = new Accounts();
        account.setAccountNumber("123456789");
        account.setBalance(100.0);

        when(accountServices.getAccountByAccountNumber("123456789")).thenReturn(account);


        ResponseEntity<HttpStatus> response = accountController.deleteAccount("123456789");

        assertEquals(HttpStatus.OK, response.getStatusCode());


        verify(accountServices, times(1)).deleteAccount(eq("123456789"));
    }

    @Test
    void testAccountDetails() {

        Accounts account = new Accounts();
        account.setAccountNumber("123456789");
        account.setBalance(100.0);

        when(accountServices.getAccountByAccountNumber(eq("123456789"))).thenReturn(account);


        Accounts result = accountController.getAccountByAccountNumber("123456789").getBody();

        assertNotNull(result);
        assertEquals("123456789", result.getAccountNumber());
        assertEquals(100.0, result.getBalance());

        verify(accountServices, times(1)).getAccountByAccountNumber(eq("123456789"));
    }

    @Test
    void testGetAccountsByUserId() {

        List<Accounts> accountList = new ArrayList<>();
        Accounts account1 = new Accounts();
        account1.setAccountNumber("123456789");
        account1.setBalance(100.0);
        accountList.add(account1);

        when(accountServices.getAccountsByUserId(eq(123))).thenReturn(accountList);


        ResponseEntity<List<Accounts>> response = accountController.getAccountsByUserId(123);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("123456789", response.getBody().get(0).getAccountNumber());
        assertEquals(100.0, response.getBody().get(0).getBalance());

        verify(accountServices, times(1)).getAccountsByUserId(eq(123));
    }
}
