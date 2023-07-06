package com.assignment.bankManagementSystem.controller;


import com.assignment.bankManagementSystem.entities.Users;

import com.assignment.bankManagementSystem.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServices userServices;
    private Logger logger= LoggerFactory.getLogger(UserController.class);



    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers(){
    logger.info("Fetching all users");
        List<Users> usersList= userServices.getUsers();
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
        if (user!=null){
            logger.info("User found");
            return ResponseEntity.ok(user);
        }
        else {
            logger.warn("User not found");
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/users")
    public ResponseEntity<Users> addUser(@RequestBody Users userToAdd){
        logger.info("Adding User {}",userToAdd);
        Users user=userServices.addUser(userToAdd);
        return ResponseEntity.ok(user);

    }
    @PutMapping("/users/{userId}")
    public ResponseEntity<Users> putUser(@RequestParam int userId,@RequestBody Users detailsToUpdate)    {
        logger.info("Updating details of user {}",userId);
        Users updatedUser =userServices.updateUserDetails(userId,detailsToUpdate);
        return ResponseEntity.ok(updatedUser);

    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int userId){
        logger.info("Deleting user br userId {}",userId);
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
