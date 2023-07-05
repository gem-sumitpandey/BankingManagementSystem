package com.assignment.bankManagementSystem.dao;
import com.assignment.bankManagementSystem.entities.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDao extends JpaRepository<Accounts,String> {
    List<Accounts> findAllByUserUserId(int userId);


}
