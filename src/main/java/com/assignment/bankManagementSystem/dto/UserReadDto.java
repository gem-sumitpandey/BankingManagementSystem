package com.assignment.bankManagementSystem.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {

    private String firstName;
    private String lastName;
    private int age;
    private String address;
}
