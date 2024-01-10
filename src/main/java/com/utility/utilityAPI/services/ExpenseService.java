package com.utility.utilityAPI.services;

import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.repositories.ExpenseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    ExpenseRepo expenseRepo;
    public ExpenseService(ExpenseRepo expenseRepo){
        this.expenseRepo=expenseRepo;
    }

    public boolean addExpense(Expense expense){
        expenseRepo.save(expense);
        return true;
    }


    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public boolean payExpense(int txnId) {
        expenseRepo.payExpense(txnId);
        return true;
    }
}
