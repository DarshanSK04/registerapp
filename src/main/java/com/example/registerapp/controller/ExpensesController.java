package com.example.registerapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.event.spi.LockEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.registerapp.Services.ExpenseService;
import com.example.registerapp.UserData.Expense;
import com.example.registerapp.UserRepository.ExpenseRepos;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:4200")
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


    @GetMapping("/editExpense/{userId}")
    public Expense returnExpaneToEdit(@PathVariable Long userId)
    {
        Expense returnExpense=expenseRepos.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));
        return returnExpense;
    }


    @PutMapping("/updateExpense/{userId}")
    public Expense editExpense(@PathVariable Long userId, @RequestBody Expense updatedExpense) {
        Expense existing = expenseRepos.findById(userId)
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
