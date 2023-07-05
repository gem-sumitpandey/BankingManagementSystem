package com.assignment.bankManagementSystem.services;

import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserServices{

    public List<Users> getUsers();
    public Users getUserById(int userId);

    public Users addUser(Users ac);


    Users updateUserDetails(int userId, Users user);

    void deleteUser(int userId);


}
