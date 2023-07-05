package com.assignment.bankManagementSystem.servicesTests;

import com.assignment.bankManagementSystem.dao.AccountDao;
import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.services.AccountServicesImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class AccountServicesImplementationTest {

        @Mock
        private AccountDao accountDao;

        @InjectMocks
        private AccountServicesImplementation accountServices;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testOpenAccount() {
            Accounts account = new Accounts();
            account.setAccountNumber("1234");
            when(accountDao.save(any(Accounts.class))).thenReturn(account);

            Accounts openedAccount = accountServices.openAccount(account);

            assertNotNull(openedAccount);
            assertEquals("1234", openedAccount.getAccountNumber());
            verify(accountDao, times(1)).save(account);
        }

        @Test
        void testGetAccountByAccountNumber() {
            Accounts account = new Accounts();
            account.setAccountNumber("1234");
            when(accountDao.findById("1234")).thenReturn(Optional.of(account));

            Accounts foundAccount = accountServices.getAccountByAccountNumber("1234");

            assertNotNull(foundAccount);
            assertEquals("1234", foundAccount.getAccountNumber());
            verify(accountDao, times(1)).findById("1234");
        }

        @Test
        void testDeposit() {
            Accounts account = new Accounts();
            account.setAccountNumber("1234");
            account.setBalance(100.0);
            when(accountDao.findById("1234")).thenReturn(Optional.of(account));
            when(accountDao.save(any(Accounts.class))).thenReturn(account);

            Accounts updatedAccount = accountServices.deposit("1234", 50.0);

            assertNotNull(updatedAccount);
            assertEquals(150.0, updatedAccount.getBalance());
            verify(accountDao, times(1)).findById("1234");
            verify(accountDao, times(1)).save(account);
        }

        @Test
        void testWithdrawSufficientBalance() {
            Accounts account = new Accounts();
            account.setAccountNumber("1234");
            account.setBalance(100.0);
            when(accountDao.findById("1234")).thenReturn(Optional.of(account));
            when(accountDao.save(any(Accounts.class))).thenReturn(account);

            Accounts updatedAccount = accountServices.withdraw("1234", 50.0);

            assertNotNull(updatedAccount);
            assertEquals(50.0, updatedAccount.getBalance());
            verify(accountDao, times(1)).findById("1234");
            verify(accountDao, times(1)).save(account);
        }

        @Test
        void testWithdrawInsufficientBalance() {
            Accounts account = new Accounts();
            account.setAccountNumber("1234");
            account.setBalance(100.0);
            when(accountDao.findById("1234")).thenReturn(Optional.of(account));
            when(accountDao.save(any(Accounts.class))).thenReturn(account);

            Accounts updatedAccount = accountServices.withdraw("1234", 150.0);

            assertNotNull(updatedAccount);
            assertEquals(100.0, updatedAccount.getBalance());

            verify(accountDao, times(1)).findById("1234");
            verify(accountDao, times(1)).save(account);
        }

        @Test
        void testBalanceEnquiry() {
            Accounts account = new Accounts();
            account.setAccountNumber("1234");
            account.setBalance(100.0);
            when(accountDao.findById("1234")).thenReturn(Optional.of(account));

            Double balance = accountServices.balanceEnquiry("1234");

            assertNotNull(balance);
            assertEquals(100.0, balance);
            verify(accountDao, times(1)).findById("1234");
        }

        @Test
        void testGetAccountsByUserId() {
            Accounts account1 = new Accounts();
            account1.setAccountNumber("1234");
            Accounts account2 = new Accounts();
            account2.setAccountNumber("7890");
            List<Accounts> accounts = Arrays.asList(account1, account2);
            when(accountDao.findAllByUserUserId(1)).thenReturn(accounts);

            List<Accounts> foundAccounts = accountServices.getAccountsByUserId(1);

            assertNotNull(foundAccounts);
            assertEquals(2, foundAccounts.size());
            assertEquals("1234", foundAccounts.get(0).getAccountNumber());
            assertEquals("7890", foundAccounts.get(1).getAccountNumber());
            verify(accountDao, times(1)).findAllByUserUserId(1);
        }

        @Test
        void testDeleteAccount() {
            Accounts account = new Accounts();
            account.setAccountNumber("1234");
            when(accountDao.findById("1234")).thenReturn(Optional.of(account));

            accountServices.deleteAccount("1234");

            verify(accountDao, times(1)).findById("1234");
            verify(accountDao, times(1)).deleteById("1234");
        }

        @Test
        void testDeleteAccountAccountNotExists() {
            when(accountDao.findById("1234")).thenReturn(Optional.empty());

            accountServices.deleteAccount("1234");

            verify(accountDao, times(1)).findById("1234");
            verify(accountDao, never()).deleteById(anyString());
        }
        @Test
        void testGetAccountByAccountNumber_AccountNotExists() {
            when(accountDao.findById("1234")).thenReturn(Optional.empty());

            Accounts foundAccount = accountServices.getAccountByAccountNumber("1234");

            assertNull(foundAccount);
            verify(accountDao, times(1)).findById("1234");
        }

        @Test
        void testDeposit_NonExistingAccount() {
            when(accountDao.findById("1234")).thenReturn(Optional.empty());

            Accounts updatedAccount = accountServices.deposit("1234", 50.0);

            assertNull(updatedAccount);
            verify(accountDao, times(1)).findById("1234");
            verify(accountDao, never()).save(any(Accounts.class));
        }

        @Test
        void testWithdraw_NonExistingAccount() {
            when(accountDao.findById("1234")).thenReturn(Optional.empty());

            Accounts updatedAccount = accountServices.withdraw("1234", 50.0);

            assertNull(updatedAccount);
            verify(accountDao, times(1)).findById("1234");
            verify(accountDao, never()).save(any(Accounts.class));
        }

        @Test
        void testGetAccountsByUserId_NoAccountsFound() {
            when(accountDao.findAllByUserUserId(1)).thenReturn(Collections.emptyList());

            List<Accounts> foundAccounts = accountServices.getAccountsByUserId(1);

            assertNotNull(foundAccounts);
            assertTrue(foundAccounts.isEmpty());
            verify(accountDao, times(1)).findAllByUserUserId(1);
        }


    }



