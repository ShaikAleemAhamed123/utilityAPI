package com.utility.utilityAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utility.utilityAPI.models.PayLoad;
import com.utility.utilityAPI.models.User;
import com.utility.utilityAPI.services.AuthService;
import com.utility.utilityAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    UserService userService;
    ObjectMapper objectMapper;
    AuthService authService;
    public AuthController(UserService userService, AuthService authService, ObjectMapper objectMapper){
        this.userService=userService;
        this.objectMapper=objectMapper;
        this.authService=authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody String data)  {
        PayLoad payLoad=PayLoadJsonParser(data);
        if(payLoad==null) return (ResponseEntity<?>) ResponseEntity.status(401).body("Please send user details in a valid format !");
        String token=authService.authenticateUser(payLoad.getUserHandle(),payLoad.getPassword());
        if(Objects.equals(token, "")) return ResponseEntity.status(401).body("Incorrect Credentials :(");
        return ResponseEntity.status(200).body(token);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody String data){
        User user=UserJsonParser(data);
        if(user==null) return ResponseEntity.status(400).body("Invalid data format in Body :(");
        if(userService.addNewUser(user)){
            return ResponseEntity.ok("User Added Successfully");
        }
        return ResponseEntity.status(400).body("Server difficulty in adding user to the database !");
    }

    public PayLoad PayLoadJsonParser(String data){

        try{
            return objectMapper.readValue(data, PayLoad.class);
        }
        catch(Exception e){
            System.out.println("Exception while parsing json user data : "+ e.getMessage());
            return null;
        }
    }

    public User UserJsonParser(String data){

        try{
            return objectMapper.readValue(data, User.class);
        }
        catch(Exception e){
            System.out.println("Exception while parsing json user data : "+ e.getMessage());
            return null;
        }
    }

}
