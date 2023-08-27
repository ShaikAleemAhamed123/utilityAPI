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
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllExpenses(){
        return ResponseEntity.ok().body(expenseService.getAllExpenses());
    }
    @PostMapping("/addExpense")
    public ResponseEntity<?> addExpense(@RequestBody Expense expense, @RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        if(expense==null) return ResponseEntity.status(500).body("The format of expense sent in body is not valid !");
        if(expenseService.addExpense(expense)) return ResponseEntity.status(201).body("Expense Added Successfully !");
        return ResponseEntity.status(500).body("Server Difficulty adding the expense to the database !");
    }


}
