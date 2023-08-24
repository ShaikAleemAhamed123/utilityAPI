package com.utility.utilityAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.utilityAPI.services.AuthService;
import com.utility.utilityAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getDebts(@RequestHeader("userHandle") String userHandle, @RequestParam(defaultValue = "") String tag, @RequestHeader("Authorization") String authorizationHeader){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(userService.getDebtsByTag(userHandle,tag));
    }

    @GetMapping("/credits")
    public ResponseEntity<?> getCredits(@RequestHeader("userHandle") String userHandle, @RequestParam(defaultValue = "") String tag, @RequestHeader("Authorization") String authorizationHeader){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(userService.getCreditsByTag(userHandle, tag));
    }

}
