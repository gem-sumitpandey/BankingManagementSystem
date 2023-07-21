package com.assignment.bankManagementSystem.services;
import com.assignment.bankManagementSystem.dto.UserReadDto;
import com.assignment.bankManagementSystem.dto.UserWriteDto;
import com.assignment.bankManagementSystem.entities.Users;


import java.util.List;

public interface UserServices{

    public List<UserReadDto> getUsers();
    public Users getUserById(int userId);

    public Users addUser(UserWriteDto ac);


    UserReadDto updateUserDetails(int userId, UserWriteDto user);

    void deleteUser(int userId);


}
