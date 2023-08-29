package com.utility.utilityAPI.controllers;

import com.utility.utilityAPI.models.PayLoad;
import com.utility.utilityAPI.models.UserData;
import com.utility.utilityAPI.services.AuthService;
import com.utility.utilityAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    UserService userService;
    AuthService authService;
    public AuthController(UserService userService, AuthService authService){
        this.userService=userService;
        this.authService=authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody PayLoad body)  {
        if(body==null) return ResponseEntity.status(401).body("Please send user details in a valid format !");
        String token=authService.authenticateUser(body.getUserHandle(),body.getPassword());
        if(Objects.equals(token, "")) return ResponseEntity.status(401).body("Incorrect Credentials :(");
        return ResponseEntity.status(200).body(token);
    }


    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserData userData){
        if(userData ==null) return ResponseEntity.status(400).body("Invalid data format in Body :(");
        if(userService.addNewUser(userData)){
            return ResponseEntity.status(201).body("User Added Successfully");
        }
        return ResponseEntity.status(400).body("Server difficulty in adding user to the database !");
    }

}
