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


                user = entityManager.merge(user);
                entityManager.flush();
        }

        @Test
        public void testFindAllByUserUserId() {

                Accounts account1 = new Accounts();
                Accounts account2 = new Accounts();


                account1.setUser(user);
                account2.setUser(user);

                entityManager.persist(account1);
                entityManager.persist(account2);
                entityManager.flush();

                List<Accounts> accounts = accountDao.findAllByUserUserId(user.getUserId());

                Assertions.assertFalse(accounts.isEmpty());
                Assertions.assertEquals(2, accounts.size());
        }
}
