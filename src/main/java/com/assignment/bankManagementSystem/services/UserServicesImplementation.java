package com.assignment.bankManagementSystem.services;

import com.assignment.bankManagementSystem.dao.UserDao;
import com.assignment.bankManagementSystem.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServicesImplementation implements UserServices{
    @Autowired
    private UserDao userDao;


    @Override
    public List<Users> getUsers() {

        return userDao.findAll();
    }
    @Override
    public Users getUserById(int userId){

        Users users=userDao.findById(userId).orElse(null);{
            if(users!=null){
                return users;
            }
            else{
                return null;
            }
        }
    }

    @Override
    public Users addUser(Users ac) {
       return userDao.save(ac);

    }

    @Override
    public Users updateUserDetails(int userId, Users user){
        Users users=userDao.findById(userId).orElse(null);
        if (users!=null){
            return  userDao.save(user);
        }
        else{
            return null;
        }
    }

    @Override
    public void deleteUser(int userId) {
        Users user=userDao.findById(userId).orElse(null);
        if (user!=null){
            userDao.deleteById(userId);
        }

    }



}
