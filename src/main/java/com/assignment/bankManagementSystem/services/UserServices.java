package com.assignment.bankManagementSystem.services;
import com.assignment.bankManagementSystem.dto.UserReadDto;
import com.assignment.bankManagementSystem.dto.UserUpdateDto;
import com.assignment.bankManagementSystem.dto.UserWriteDto;
import com.assignment.bankManagementSystem.entities.Users;


import java.util.List;

public interface UserServices{

    public List<UserReadDto> getUsers();
    public Users getUserById(int userId);

    public Users addUser(UserWriteDto ac);




    Users updateUserDetails(int userId, UserUpdateDto userUpdateDto);

    void deleteUser(int userId);


}
