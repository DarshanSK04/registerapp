package com.example.registerapp.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import java.lang.String;
import com.example.registerapp.UserData.Expense;
import com.example.registerapp.UserData.Users;
import com.example.registerapp.UserRepository.ExpenseRepos;
import com.example.registerapp.UserRepository.UserRepos;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepos expenseRepos;

    @Autowired
    UserRepos userRepos;

    
    

    public Expense addExpense(String email,Expense expense)
    {
        Users user=userRepos.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
        expense.setUser(user);
        return expenseRepos.save(expense);
    }
    public List<Expense> getExpenses (String email)
    {
        Users user=userRepos.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
        return expenseRepos.findByUser(user);
    }
    public void deleteExpenses(Long id)
    {
        expenseRepos.deleteById(id);
    }
    public List<Expense> getonlineExpense(String email) {
        // TODO Auto-generated method stub
        Users user=userRepos.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
        List<Expense>allOnlinExpenses=expenseRepos.findByUser(user);
        List<Expense>sortedOnlineList=new ArrayList<>();
        for(Expense online: allOnlinExpenses)
        {
            if(!online.getPaymentMethod().equals("Cash")&& !online.getPaymentMethod().equals("Card"))
            {
                sortedOnlineList.add(online);
            }
        }
        return sortedOnlineList;
        
    }
    public List<Expense> getOfflineExpenses(String email) {
        // TODO Auto-generated method stub
        Users user=userRepos.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
        List<Expense>allOfflineExpenses=expenseRepos.findByUser(user);
        List<Expense>sortedOfflineList=new ArrayList<>();
        for(Expense online: allOfflineExpenses)
        {
            if(online.getPaymentMethod().equals("Cash")||online.getPaymentMethod().equals("Card"))
            {
                sortedOfflineList.add(online);
            }
        }
        return sortedOfflineList;
    }
    public List<Expense>getBillsExpenses(String category)
    {
        return expenseRepos.findByCategory(category);
    }

}
