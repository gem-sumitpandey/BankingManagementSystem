package com.assignment.bankManagementSystem.entities;

import com.assignment.bankManagementSystem.utils.AccountUtils;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;



import javax.persistence.*;


import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Accounts {

    @Id
    private String accountNumber;
    private String accountType;
    private Double balance;
    private String branch;
    private LocalDateTime accountCreatedOn=LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name="userId")
    private Users user;

    @PrePersist
    private void generateAccountNumber() {

        this.accountNumber = AccountUtils.generateRandomAccountNumber();
    }


}
