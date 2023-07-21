package com.assignment.bankManagementSystem.controller;


import com.assignment.bankManagementSystem.dto.UserReadDto;
import com.assignment.bankManagementSystem.dto.UserWriteDto;
import com.assignment.bankManagementSystem.entities.Users;

import com.assignment.bankManagementSystem.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServices userServices;
    private Logger logger= LoggerFactory.getLogger(UserController.class);



    @GetMapping("/users")
    public ResponseEntity<List<UserReadDto>> getUsers(){
    logger.info("Fetching all users");
        List<UserReadDto> usersList= userServices.getUsers();
        if (usersList.size()<=0){
            logger.info("No user exist");
            return ResponseEntity.noContent().build();
        }
        else {
            logger.warn("All users found");
            return ResponseEntity.ok(usersList);

        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Users> getUser(@PathVariable int userId){
        logger.info("Fetching user by userId {}",userId);
        Users user=userServices.getUserById(userId);
       return ResponseEntity.ok(user);
    }
    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addUser( @RequestBody @Valid UserWriteDto userToAdd){
        logger.info("Adding User {}",userToAdd);
        Users user=userServices.addUser(userToAdd);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @PutMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> putUser(@RequestParam int userId, @RequestBody @Valid UserWriteDto detailsToUpdate)    {
        logger.info("Updating details of user {}",userId);
        UserReadDto updatedUser =userServices.updateUserDetails(userId,detailsToUpdate);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int userId){
        logger.info("Deleting user userId {}",userId);
       Users user = userServices.getUserById(userId);
       if (user!=null){
           userServices.deleteUser(userId);
           logger.info("User deleted");

           return new ResponseEntity<>(HttpStatus.OK);
       }
       else{
           logger.warn("User not found");
           return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
       }
    }
}
