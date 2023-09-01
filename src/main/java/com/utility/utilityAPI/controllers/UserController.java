package com.utility.utilityAPI.controllers;

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

    public UserController(UserService userService, AuthService authService){
        this.userService = userService;
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

    @PostMapping("/pending")
    public ResponseEntity<?> updateTxnToPending(@RequestBody Long id, @RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        if(userService.updateTxnToPending(id, userHandle)){
            return ResponseEntity.status(200).body("Transaction moved to pending !");
        }
        return ResponseEntity.status(422).body("Failed to update the transaction to pending !");
    }
    @GetMapping("/pending")
    public ResponseEntity<?> getPtxns(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
       return ResponseEntity.status(200).body(userService.getPendingTxns(userHandle));
    }

    @PostMapping("/paid")
    public ResponseEntity<?> updateTxnToPaid(@RequestBody Long id, @RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        if(userService.updateTxnToPaid(id, userHandle)){
            return ResponseEntity.status(200).body("Transaction moved to paid !");
        }
        return ResponseEntity.status(422).body("Failed to update the transaction to paid !");
    }
    @GetMapping("/paid")
    public ResponseEntity<?> getPaidTxns(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(userService.getPaidTxns(userHandle));
    }
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedTxns(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(userService.getReceivedTxns(userHandle));
    }

}
