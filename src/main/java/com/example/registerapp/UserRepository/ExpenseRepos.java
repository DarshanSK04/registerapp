package com.example.registerapp.UserRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.registerapp.UserData.Expense;
import com.example.registerapp.UserData.Users;

@Repository
public interface ExpenseRepos extends JpaRepository<Expense,Long>{
    List<Expense>findByUser(Users user);
    List<Expense>findByPaymentMethod(String paymentMethod);
    List<Expense>findByCategory(String category);
    
    List<Expense> findByUserIdAndPaymentMethodIn(Long id, List<String>modes);

}
