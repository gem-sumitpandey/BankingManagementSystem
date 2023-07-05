package com.assignment.bankManagementSystem.controller;


import com.assignment.bankManagementSystem.entities.Users;

import com.assignment.bankManagementSystem.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServices userServices;



    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers(){

        List<Users> usersList= userServices.getUsers();
        if (usersList.size()<=0){
            return ResponseEntity.noContent().build();
        }
        else { return ResponseEntity.ok(usersList);

        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Users> getUser(@PathVariable int userId){

        Users user=userServices.getUserById(userId);
        if (user!=null){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/users")
    public ResponseEntity<Users> addUser(@RequestBody Users userToAdd){
        Users user=userServices.addUser(userToAdd);
        return ResponseEntity.ok(user);

    }
    @PutMapping("/users/{userId}")
    public ResponseEntity<Users> putUser(@RequestParam int userId,@RequestBody Users detailsToUpdate)    {
        Users updatedUser =userServices.updateUserDetails(userId,detailsToUpdate);
        return ResponseEntity.ok(updatedUser);

    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int userId){
       Users user = userServices.getUserById(userId);
       if (user!=null){
           userServices.deleteUser(userId);
           return new ResponseEntity<>(HttpStatus.OK);
       }
       else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
       }
    }



}
