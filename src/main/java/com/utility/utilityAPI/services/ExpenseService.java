package com.utility.utilityAPI.services;

import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.repositories.ExpenseRepo;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    ExpenseRepo expenseRepo;

    public ExpenseService(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    public boolean addExpense(Expense expense) {
        expenseRepo.save(expense);
        return true;
    }
}