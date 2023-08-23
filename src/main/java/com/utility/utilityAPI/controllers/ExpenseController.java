package com.utility.utilityAPI.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.services.ExpenseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExpenseController {

    ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;
    }
    @PostMapping("/addExpense")
    public boolean addExpense(@RequestBody String body){
        Expense expense=ExpenseJsonParser(body);
        if(expense==null) return false;
        return expenseService.addExpense(expense);
    }

    public Expense ExpenseJsonParser(String data){
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            return objectMapper.readValue(data,Expense.class);
        }
        catch(Exception e){
            System.out.println("Exception while parsing json user data : "+ e.getMessage());
        }
        return null;
    }

}
