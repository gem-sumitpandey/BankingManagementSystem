package com.assignment.bankManagementSystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Users {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String aadharNumber;

    @Getter
    @Setter
    private java.sql.Date dateOfBirth;

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String mobileNo;

    @Getter
    @Setter
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Accounts> accountsList;






}
