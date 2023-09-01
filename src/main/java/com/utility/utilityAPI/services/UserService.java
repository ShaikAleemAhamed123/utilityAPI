package com.utility.utilityAPI.services;

import com.utility.utilityAPI.models.UserData;
import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.repositories.ExpenseRepo;
import com.utility.utilityAPI.repositories.UserRepo;
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

    public boolean addNewUser(UserData userData){
        if(userRepo.findByUserHandle(userData.getUserHandle())!=null){
            return false;
        }
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        UserData createdUserData =userRepo.save(userData);
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
        UserData userData =userRepo.findByUserHandle(userName);
        if(userData ==null) return false;
        return JwtVerifier.verifyToken(jwtToken, userName);
    }

    public boolean updateTxnToPending(int id) {
        try{
            userRepo.updateTxnToPending(id);
            return true;
        }
        catch(Exception e){
            System.out.println("Exception while updating the txn to pending with Exception :: "+e);
            return false;
        }

    }

    public List<Expense> getPendingTxns(String userHandle) {
        return userRepo.findPendingTxns(userHandle);
    }

    public boolean updateTxnToPaid(int id) {
        try{
            userRepo.updateTxnToPaid(id);
            return true;
        }
        catch(Exception e){
            System.out.println("Exception while updating the txn to paid with Exception :: "+e);
            return false;
        }
    }

    public List<Expense> getPaidTxns(String userHandle) {
        return userRepo.findPaidTxns(userHandle);
    }

    public List<Expense> getReceivedTxns(String userHandle) {
        return userRepo.findReceivedTxns(userHandle);
    }
}
