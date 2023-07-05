package com.assignment.bankManagementSystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;


import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accounts {



    @Id
    private String accountNumber;



    private String accountType;
    private Double balance;
    private String branch;
    private LocalDateTime accountCreatedOn=LocalDateTime.now();
    private LocalDateTime accountUpdatedAt=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="userId")
    private Users user;


}
