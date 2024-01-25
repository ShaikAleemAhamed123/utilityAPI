package com.utility.utilityAPI.services;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.utility.utilityAPI.models.UserData;
import com.utility.utilityAPI.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    UserService userService;
    UserRepo userRepo;
    PasswordEncoder passwordEncoder;
    JwtGenerator jwtGenerator;
    public AuthService(UserService userService, UserRepo userRepo, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator){
        this.userService=userService;
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
        this.jwtGenerator=jwtGenerator;
    }

    public String authenticateUser(String userName, String password){
        UserData retrieved_App_userData = userRepo.findByUserHandle(userName);
        if(retrieved_App_userData ==null) return "";
        try {
            if(passwordEncoder.matches(password, retrieved_App_userData.getPassword())){
                String token = jwtGenerator.generateToken(userName);
                System.out.println("From the authenticate user method in the user service class :: Token : "+token);
                return token;
            }
            return "";

        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            return "";
        }

    }
    public boolean verifyToken(String authorizationHeader, String userName) {
        String token="";
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            System.out.println("Token extracted successfully ! + token : "+token);
            boolean userAvailable = userService.userAvailable(userName);
            if(!userAvailable){
                System.out.println("User not registered !");
                return false;
            }
            return JwtVerifier.verifyToken(token, userName);
        }
        System.out.println("No token found !");
        return false; // No token found
    }
}
