package com.utility.utilityAPI.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.services.AuthService;
import com.utility.utilityAPI.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExpenseController {

    ExpenseService expenseService;
    AuthService authService;
    public ExpenseController(ExpenseService expenseService, AuthService authService){
        this.expenseService=expenseService;
        this.authService=authService;
    }
    @PostMapping("/addExpense")
    public ResponseEntity<?> addExpense(@RequestBody String body, @RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        Expense expense=ExpenseJsonParser(body);
        if(expense==null) return ResponseEntity.status(500).body("Difficulty adding the expense to the database !");
        return ResponseEntity.status(200).body(expenseService.addExpense(expense));
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
