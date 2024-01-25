package com.utility.utilityAPI.services;

import com.utility.utilityAPI.models.Expense;
import com.utility.utilityAPI.repositories.ledgerRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ledgerService {

    ledgerRepo ledgerRepo;
    public ledgerService(ledgerRepo ledgerRepo){
        this.ledgerRepo = ledgerRepo;
    }

    public boolean addExpense(Expense expense){
        ledgerRepo.save(expense);
        return true;
    }

    public List<Expense> getCreditsByTag(String user, String tag) {
        if(Objects.equals(tag, "")) return ledgerRepo.findByPayee(user);
        return ledgerRepo.findByPayeeAndTag(user, tag);
    }

    public List<Expense> getDebtsByTag(String user, String tag) {
        if(Objects.equals(tag, "")) return ledgerRepo.findByPayer(user);
        return ledgerRepo.findByPayerAndTag(user,tag);
    }



    public boolean updateTxnToPending(Long id, String userHandle) {
        Optional<Expense> e= ledgerRepo.findById(id);
        if(e.isEmpty()) return false;
        final boolean[] flag = {false};
        e.ifPresent(expense -> {
            String  payer = expense.getPayer();
            flag[0] = Objects.equals(payer, userHandle);
        });
        if(flag[0]){
            ledgerRepo.updateTxnToPending(id);
            return true;
        }
        return false;

    }

    public List<Expense> getPendingTxns(String userHandle) {
        return ledgerRepo.findPendingTxns(userHandle);
    }

    public boolean updateTxnToPaid(Long id, String userHandle) {
        Optional<Expense> e= ledgerRepo.findById(id);
        if(e.isEmpty()) return false;
        final boolean[] flag = {false};
        e.ifPresent(expense -> {
            String  payee = expense.getPayee();
            flag[0] = Objects.equals(payee, userHandle);
        });
        if(flag[0]){
            ledgerRepo.updateTxnToPaid(id);
            return true;
        }
        return false;
    }

    public List<Expense> getPaidTxns(String userHandle) {
        return ledgerRepo.findPaidTxns(userHandle);
    }

    public List<Expense> getReceivedTxns(String userHandle) {
        return ledgerRepo.findReceivedTxns(userHandle);
    }

    public List<Expense> getPendingDebitTxns(String userHandle) {
        return ledgerRepo.getPendingDebitTxns(userHandle);
    }

    public List<Expense> getPendingCreditTxns(String userHandle) {
        return ledgerRepo.getPendingCreditTxns(userHandle);
    }
}
