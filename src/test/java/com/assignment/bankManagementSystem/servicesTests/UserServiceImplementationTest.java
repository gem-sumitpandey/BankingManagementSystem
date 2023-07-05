package com.assignment.bankManagementSystem.servicesTests;
import com.assignment.bankManagementSystem.dao.UserDao;
import com.assignment.bankManagementSystem.entities.Users;
import com.assignment.bankManagementSystem.services.UserServicesImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplementationTest {

        @Mock
        private UserDao userDao;

        @InjectMocks
        private UserServicesImplementation userServices;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testGetUsers() {
            Users user1 = new Users();
            Users user2 = new Users();
            List<Users> usersList = Arrays.asList(user1, user2);
            when(userDao.findAll()).thenReturn(usersList);

            List<Users> foundUsers = userServices.getUsers();

            assertNotNull(foundUsers);
            assertEquals(2, foundUsers.size());
            verify(userDao, times(1)).findAll();

        }

        @Test
        void testGetUserByIdUserExists() {
            Users user = new Users();
            user.setUserId(1);
            when(userDao.findById(1)).thenReturn(Optional.of(user));

            Users foundUser = userServices.getUserById(1);

            assertNotNull(foundUser);
            assertEquals(1, foundUser.getUserId());
            verify(userDao, times(1)).findById(1);
        }

        @Test
        void testGetUserByIdUserNotExists() {
            when(userDao.findById(1)).thenReturn(Optional.empty());

            Users foundUser = userServices.getUserById(1);

            assertNull(foundUser);
            verify(userDao, times(1)).findById(1);
        }

        @Test
        void testAddUser() {
            Users user = new Users();
            user.setUserId(1);
            when(userDao.save(any(Users.class))).thenReturn(user);

            Users addedUser = userServices.addUser(user);

            assertNotNull(addedUser);
            assertEquals(1, addedUser.getUserId());
            verify(userDao, times(1)).save(user);
        }

        @Test
        void testUpdateUserDetailsUserExists() {
            Users user = new Users();
            user.setUserId(1);
            when(userDao.findById(1)).thenReturn(Optional.of(user));
            when(userDao.save(any(Users.class))).thenReturn(user);

            Users updatedUser = userServices.updateUserDetails(1, user);

            assertNotNull(updatedUser);
            assertEquals(1, updatedUser.getUserId());
            verify(userDao, times(1)).findById(1);
            verify(userDao, times(1)).save(user);
        }

        @Test
        void testUpdateUserDetailsUserNotExists() {
            when(userDao.findById(1)).thenReturn(Optional.empty());

            Users updatedUser = userServices.updateUserDetails(1, new Users());

            assertNull(updatedUser);
            verify(userDao, times(1)).findById(1);
            verify(userDao, never()).save(any(Users.class));
        }

        @Test
        void testDeleteUser() {
            Users user = new Users();
            user.setUserId(1);
            when(userDao.findById(1)).thenReturn(Optional.of(user));

            userServices.deleteUser(1);

            verify(userDao, times(1)).findById(1);
            verify(userDao, times(1)).deleteById(1);
        }
        @Test
        void testDeleteUserUserNotExist(){
            when(userDao.findById(12)).thenReturn(Optional.empty());
             userServices.deleteUser(12);
            verify(userDao,times(1)).findById(12);
            verify(userDao,never()).deleteById(anyInt());

        }

    }



