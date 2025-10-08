package com.example.registerapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.registerapp.Services.ExpenseService;
import com.example.registerapp.UserData.Expense;
import com.example.registerapp.UserRepository.ExpenseRepos;


@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = {
    "http://localhost:4200",
    "https://expense-frontend-mrqp.onrender.com"
})

public class ExpensesController {

    @Autowired
    ExpenseService expenseService;

    @Autowired
    ExpenseRepos expenseRepos;

    @PostMapping("/{email}/add")
    public Expense addExpense(@PathVariable String email, @RequestBody Expense expense) {
        return expenseService.addExpense(email, expense);
    }

    @GetMapping("/viewExpenses/{email}")
    public List<Expense> getExpense(@PathVariable String email) {
        return expenseService.getExpenses(email);
    }

    @GetMapping("/onlineExpenses/{id}")
    public List<Expense> getOnliExpenses(@PathVariable Long id) {
        
        return expenseRepos.findByUserIdAndPaymentMethodIn(id,List.of("UPI"));
    }

    @GetMapping("/offlineExpenses/{id}")
    public List<Expense> getOfflineExpenses(@PathVariable Long id) {
        
        return expenseRepos.findByUserIdAndPaymentMethodIn(id,List.of("Cash","Card"));
    }


    @GetMapping("/editExpense/{expenseId}")
    public Expense returnExpaneToEdit(@PathVariable Long expenseId)
    {
        Expense returnExpense=expenseRepos.findById(expenseId).orElseThrow(()->new RuntimeException("User Not Found"));
        return returnExpense;
    }


    @PutMapping("/updateExpense/{expenseId}")
    public Expense editExpense(@PathVariable Long expenseId, @RequestBody Expense updatedExpense) {
        Expense existing = expenseRepos.findById(expenseId)
            .orElseThrow(() -> new RuntimeException("Expense Not Found"));

        existing.setDescription(updatedExpense.getDescription());
        existing.setCategory(updatedExpense.getCategory());
        existing.setAmount(updatedExpense.getAmount());
        existing.setPaymentMethod(updatedExpense.getPaymentMethod());
        existing.setTransactionDate(updatedExpense.getTransactionDate());

        return expenseRepos.save(existing);  
    }

    @GetMapping("/expensesbymonth/{email}")
    public Map<String, List<Expense>> getExpensesByMonth(@PathVariable String email) {
        List<Expense> allExpenses=expenseService.getExpenses(email);
        return
        allExpenses.stream().collect(Collectors.groupingBy(expense->
        {
            LocalDate date=expense.getTransactionDate();
            return date.getMonth().toString()+" "+date.getYear();
        }));
    }
    

    @DeleteMapping("/deleteExpense/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenses(id);
    }
    @GetMapping("/Darshan/{category}")
    public List<Expense> returnBillsExpense(@PathVariable String category) {
        return expenseService.getBillsExpenses(category);
    }
    
}
