package com.assignment.bankManagementSystem.daoTests;


import com.assignment.bankManagementSystem.dao.AccountDao;
import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.entities.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.test.context.TestPropertySource;


import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test-application.properties")

public class AccountDaoTest {


@Autowired
private AccountDao accountDao;

@Autowired
private TestEntityManager entityManager;

private Users user;

@BeforeEach
public void setup() {
        user = new Users();
        user.setUserId(100);

        entityManager.persist(user);
        entityManager.flush();
        }

@Test
public void testFindAllByUserUserId() {
        Accounts account1 = new Accounts("ACC001", "Savings", 1000.0, "Branch A", LocalDateTime.now(), LocalDateTime.now(), user);
        Accounts account2 = new Accounts("ACC002", "Current", 2000.0, "Branch B", LocalDateTime.now(), LocalDateTime.now(), user);
        entityManager.persist(account1);
        entityManager.persist(account2);
        entityManager.flush();

        List<Accounts> accounts = accountDao.findAllByUserUserId(user.getUserId());


        Assertions.assertFalse(accounts.isEmpty());

        Assertions.assertEquals(2, accounts.size());
        }



        }
