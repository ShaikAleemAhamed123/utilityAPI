package com.utility.utilityAPI.services;

import com.utility.utilityAPI.models.UserData;
import com.utility.utilityAPI.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepo userRepo;
    PasswordEncoder passwordEncoder;


    public UserService(UserRepo userRepo,PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
    }

    public boolean addNewUser(UserData userData){
        if(userRepo.findByUserHandle(userData.getUserHandle())!=null){
            return false;
        }
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        UserData createdUserData =userRepo.save(userData);
        return true;
    }
    public boolean userAvailable(String userName) {
        UserData userData =userRepo.findByUserHandle(userName);
        if(userData == null) return false;
        return true;
    }
}
