package com.utility.utilityAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.services.AuthService;
import com.utility.utilityAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    UserService userService;
    AuthService authService;
    ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper, AuthService authService){
        this.userService = userService;
        this.objectMapper=objectMapper;
        this.authService=authService;
    }

    @GetMapping("/debts")
    public List<Expense> getDebts(@RequestParam String user, @RequestParam(defaultValue = "") String tag, @RequestHeader("Authorization") String authorizationHeader){
        if(!authService.verifyToken(authorizationHeader, user)) ResponseEntity.status(401).body("User not authenticated");
        return userService.getDebtsByTag(user,tag);
    }

    @GetMapping("/credits")
    public ResponseEntity<?> getCredits(@RequestParam String user, @RequestParam(defaultValue = "") String tag, @RequestHeader("Authorization") String authorizationHeader){
        if(!authService.verifyToken(authorizationHeader, user)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(userService.getCreditsByTag(user, tag));
    }

}
