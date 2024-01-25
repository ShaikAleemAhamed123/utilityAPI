package com.utility.utilityAPI.controllers;

import com.utility.utilityAPI.services.AuthService;
import com.utility.utilityAPI.services.ledgerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledger")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ledgerController {

    AuthService authService;
    ledgerService ledgerService;

    public ledgerController( AuthService authService, ledgerService ledgerService){
        this.authService=authService;
        this.ledgerService = ledgerService;
    }

    @GetMapping("/debts")
    public ResponseEntity<?> getDebts(@RequestHeader("userHandle") String userHandle, @RequestParam(defaultValue = "") String tag, @RequestHeader("Authorization") String authorizationHeader){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(ledgerService.getDebtsByTag(userHandle,tag));
    }

    @GetMapping("/credits")
    public ResponseEntity<?> getCredits(@RequestHeader("userHandle") String userHandle, @RequestParam(defaultValue = "") String tag, @RequestHeader("Authorization") String authorizationHeader){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(ledgerService.getCreditsByTag(userHandle, tag));
    }

    @PostMapping("/toPending")
    public ResponseEntity<?> updateTxnToPending(@RequestBody Long id, @RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        if(ledgerService.updateTxnToPending(id, userHandle)){
            return ResponseEntity.status(200).body("Transaction moved to pending !");
        }
        return ResponseEntity.status(422).body("Failed to update the transaction to pending !");
    }
    @GetMapping("/pendingCredits")
    public ResponseEntity<?> getPendingCreditTxns(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
       return ResponseEntity.status(200).body(ledgerService.getPendingCreditTxns(userHandle));
    }

    @GetMapping("/pendingDebits")
    public ResponseEntity<?> getPendingDebitTxns(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(ledgerService.getPendingDebitTxns(userHandle));
    }

    @PostMapping("/pay")
    public ResponseEntity<?> updateTxnToPaid(@RequestParam Long txnId, @RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        if(ledgerService.updateTxnToPaid(txnId, userHandle)){
            return ResponseEntity.status(200).body("Transaction moved to paid !");
        }
        return ResponseEntity.status(422).body("Failed to update the transaction to paid !");
    }
    @GetMapping("/paid")
    public ResponseEntity<?> getPaidTxns(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(ledgerService.getPaidTxns(userHandle));
    }
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedTxns(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("userHandle") String userHandle){
        if(!authService.verifyToken(authorizationHeader, userHandle)) return ResponseEntity.status(401).body("User not authenticated");
        return ResponseEntity.status(200).body(ledgerService.getReceivedTxns(userHandle));
    }

}
