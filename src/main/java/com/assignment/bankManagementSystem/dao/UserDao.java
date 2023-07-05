package com.assignment.bankManagementSystem.dao;

import com.assignment.bankManagementSystem.entities.Accounts;
import com.assignment.bankManagementSystem.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static org.hibernate.loader.Loader.SELECT;


public interface UserDao extends JpaRepository<Users,Integer> {



}
