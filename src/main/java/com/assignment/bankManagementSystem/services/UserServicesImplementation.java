package com.assignment.bankManagementSystem.services;

import com.assignment.bankManagementSystem.dao.UserDao;
import com.assignment.bankManagementSystem.dto.UserReadDto;
import com.assignment.bankManagementSystem.dto.UserWriteDto;
import com.assignment.bankManagementSystem.entities.Users;
import com.assignment.bankManagementSystem.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServicesImplementation implements UserServices{
    @Autowired
    private UserDao userDao;


    private UserReadDto convertToReadDto(Users user) {
        UserReadDto readDto = new UserReadDto();
        readDto.setFirstName(user.getFirstName());
        readDto.setLastName(user.getLastName());
        readDto.setAge(user.getAge());
        readDto.setAddress(user.getAddress());
        return readDto;
    }

    private Users convertToUsersEntity(UserWriteDto writeDto) {
        Users user = new Users();
        user.setFirstName(writeDto.getFirstName());
        user.setLastName(writeDto.getLastName());
        user.setEmail(writeDto.getEmail());
        user.setMobileNo(writeDto.getMobileNo());
        user.setAddress(writeDto.getAddress());
        user.setAadharNumber(writeDto.getAadharNumber());
        user.setAge(writeDto.getAge());
        user.setDateOfBirth(writeDto.getDateOfBirth());
    return user;
    }
    public List<UserReadDto> getUsers() {
        List<Users> list=userDao.findAll();
        return list.stream().map(this::convertToReadDto).collect(Collectors.toList());

    }
    @Override
    public Users getUserById(int userId){

        Users users=userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("This Id does not exist"));

                return users;

    }

    @Override
    public Users addUser(UserWriteDto ac) {
        Users user= convertToUsersEntity(ac);
        userDao.save(user);

        return user;
    }

    @Override
    public UserReadDto updateUserDetails(int userId, UserWriteDto userWriteDto){

        Users users=userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("This Id does not exist"));

            users.setFirstName(userWriteDto.getFirstName());
            users.setLastName(userWriteDto.getLastName());
            users.setDateOfBirth(userWriteDto.getDateOfBirth());
            users.setAddress(userWriteDto.getAddress());
            users.setAadharNumber(userWriteDto.getAadharNumber());
            users.setAge(userWriteDto.getAge());
            users.setMobileNo(userWriteDto.getMobileNo());
            users.setEmail(userWriteDto.getEmail());
            users= userDao.save(users);
            return convertToReadDto(users);

        }



    @Override
    public void deleteUser(int userId) {
        Users users=userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("This Id does not exist"));

            userDao.deleteById(userId);
        }

    }




