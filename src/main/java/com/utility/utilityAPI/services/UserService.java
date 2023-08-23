package com.utility.utilityAPI.services;

import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.models.User;
import com.utility.utilityAPI.repositories.ExpenseRepo;
import com.utility.utilityAPI.repositories.UserRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    UserRepo userRepo;
    ExpenseRepo expenseRepo;
    PasswordEncoder passwordEncoder;


    public UserService(UserRepo userRepo, ExpenseRepo expenseRepo, PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.expenseRepo=expenseRepo;
        this.passwordEncoder=passwordEncoder;
    }

    public boolean addNewUser(User user){
        if(userRepo.findByUserHandle(user.getUserHandle())!=null){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        user.setRole("ROLE_USER");
        User createdUser=userRepo.save(user);
        return true;
    }

    public List<Expense> getCreditsByTag(String user, String tag) {
        if(Objects.equals(tag, "")) return expenseRepo.findByPayee(user);
        return expenseRepo.findByPayeeAndTag(user, tag);
    }

    public List<Expense> getDebtsByTag(String user, String tag) {
        if(Objects.equals(tag, "")) return expenseRepo.findByPayer(user);
        return expenseRepo.findByPayerAndTag(user,tag);
    }

    public boolean verifyToken(String jwtToken, String userName) {
        User user=userRepo.findByUserHandle(userName);
        if(user==null) return false;
        return JwtVerifier.verifyToken(jwtToken, userName);
    }
}
