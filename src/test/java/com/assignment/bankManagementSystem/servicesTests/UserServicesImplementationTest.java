package com.assignment.bankManagementSystem.servicesTests;

import com.assignment.bankManagementSystem.dao.UserDao;
import com.assignment.bankManagementSystem.dto.UserReadDto;
import com.assignment.bankManagementSystem.dto.UserUpdateDto;
import com.assignment.bankManagementSystem.dto.UserWriteDto;
import com.assignment.bankManagementSystem.entities.Users;
import com.assignment.bankManagementSystem.exceptions.ConstraintException;
import com.assignment.bankManagementSystem.exceptions.ResourceNotFoundException;
import com.assignment.bankManagementSystem.services.UserServicesImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import javax.validation.constraints.Past;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicesImplementationTest{

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServicesImplementation userServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_shouldReturnListOfUsers() {

        List<Users> userList = new ArrayList<>();
        userList.add(createUser(1, "Rahul", "Singh", 30, "123 Indira Nagar"));
        userList.add(createUser(2, "Virat", "Kohli", 28, "456 Modi Nagar"));
        when(userDao.findAll()).thenReturn(userList);


        List<UserReadDto> result = userServices.getUsers();


        assertEquals(userList.size(), result.size());
        assertEquals(userList.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(userList.get(1).getLastName(), result.get(1).getLastName());

    }

    @Test
    void getUserById_existingUserId_shouldReturnUser() {

        int userId = 1;
        Users user = createUser(userId, "Rahul", "Singh", 30, "123 Indira Nagar");
        when(userDao.findById(userId)).thenReturn(Optional.of(user));


        Users result = userServices.getUserById(userId);

        assertNotNull(result);
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());

    }

    @Test
    void getUserById_nonExistingUserId_shouldThrowException() {
        // Arrange
        int userId = 100;
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userServices.getUserById(userId));
    }

    @Test
    void addUser_validUserWriteDto_shouldReturnUser() {

        UserWriteDto userWriteDto = createUserWriteDto("Rahul", "Singh", "rahul.singh@gmail.com", "1234567890", "123 Indira Nagar", "1234567890", 30, Date.valueOf("2000-01-01"));
        Users user = createUser(1, "Rahul", "Singh", 30, "123 Indira Nagar");
        when(userDao.save(any())).thenReturn(user);


        Users result = userServices.addUser(userWriteDto);


        assertNotNull(result);
        assertEquals(userWriteDto.getFirstName(), result.getFirstName());
        assertEquals(userWriteDto.getLastName(), result.getLastName());

    }

    @Test
    void updateUserDetails_existingUserIdAndValidUserUpdateDto_shouldReturnUpdatedUser() {
        int userId = 1;
        Users user = createUser(userId, "Rahul", "Singh", 30, "123 Indira Nagar");
        UserUpdateDto userUpdateDto = createUserUpdateDto("rahul.singh@gmail.com", "9876543210", "456 Palam Vihar");
        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.save(any())).thenReturn(user);


        Users result = userServices.updateUserDetails(userId, userUpdateDto);


        assertNotNull(result);
        assertEquals(userUpdateDto.getEmail(), result.getEmail());
        assertEquals(userUpdateDto.getMobileNo(), result.getMobileNo());
    }

    @Test
    void updateUserDetails_nonExistingUserId_shouldThrowException() {
        int userId = 100;
        UserUpdateDto userUpdateDto = createUserUpdateDto("jay.kumar@gmail.com", "9876543210", "456 Gandhi Street");
        when(userDao.findById(userId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> userServices.updateUserDetails(userId, userUpdateDto));
    }

    @Test
    void deleteUser_existingUserId_shouldDeleteUser() {

        int userId = 1;
        Users user = createUser(userId, "MS", "Dhoni", 40, "123 Civil Lines");
        when(userDao.findById(userId)).thenReturn(Optional.of(user));

        userServices.deleteUser(userId);

        verify(userDao, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_nonExistingUserId_shouldThrowException() {
        int userId = 100;
        when(userDao.findById(userId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> userServices.deleteUser(userId));
    }




    private Users createUser(int id, String firstName, String lastName, int age, String address) {
        Users user = new Users();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setAddress(address);
        return user;
    }


    private UserWriteDto createUserWriteDto(String firstName, String lastName, String email, String mobileNo,
                                            String address, String aadharNumber, int age, @Past(message = "Date of birth must be in the past") Date dateOfBirth) {
        UserWriteDto userWriteDto = new UserWriteDto();
        userWriteDto.setFirstName(firstName);
        userWriteDto.setLastName(lastName);
        userWriteDto.setEmail(email);
        userWriteDto.setMobileNo(mobileNo);
        userWriteDto.setAddress(address);
        userWriteDto.setAadharNumber(aadharNumber);
        userWriteDto.setAge(age);
        userWriteDto.setDateOfBirth(dateOfBirth);
        return userWriteDto;
    }


    private UserUpdateDto createUserUpdateDto(String email, String mobileNo, String address) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setEmail(email);
        userUpdateDto.setMobileNo(mobileNo);
        userUpdateDto.setAddress(address);
        return userUpdateDto;
    }
}

