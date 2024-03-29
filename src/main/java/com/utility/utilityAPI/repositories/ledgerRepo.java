package com.utility.utilityAPI.repositories;

import com.utility.utilityAPI.models.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ledgerRepo extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.status = 0 AND e.payer = :userName")
    List<Expense> findByPayer(String userName);
    @Query("SELECT e FROM Expense e WHERE e.status = 0 AND e.payee = :userName")
    List<Expense> findByPayee(String userName);
    @Query("SELECT e FROM Expense e WHERE e.status = 0 AND (e.payee = :userName AND e.tag = :tag)")
    List<Expense> findByPayeeAndTag(String userName, String tag);
    @Query("SELECT e FROM Expense e WHERE e.status = 0 AND (e.payer = :userName AND e.tag = :tag)")
    List<Expense> findByPayerAndTag(String userName, String tag);
    @Modifying
    @Transactional
    @Query("UPDATE Expense e set e.status = 1 where e.id = :txnId")
    void payExpense(int txnId);
    @Transactional
    @Modifying
    @Query("UPDATE Expense e set e.status = 1 where e.id = :id")
    void updateTxnToPending(Long id);

    @Query("SELECT e FROM Expense e WHERE e.status = 1 AND (e.payee = :userHandle OR e.payer = :userHandle)")
    List<Expense> findPendingTxns(String userHandle);

    @Transactional
    @Modifying
    @Query("UPDATE Expense e set e.status = 2 where e.id = :id")
    void updateTxnToPaid(Long id);

    @Query("SELECT e FROM Expense e WHERE e.status = 2 AND e.payer = :userHandle")
    List<Expense> findPaidTxns(String userHandle);

    @Query("SELECT e FROM Expense e WHERE e.status = 2 AND e.payee = :userHandle")
    List<Expense> findReceivedTxns(String userHandle);

    @Query("SELECT e FROM Expense e WHERE e.status = 1 AND e.payee = :userHandle")
    List<Expense> getPendingCreditTxns(String userHandle);

    @Query("SELECT e FROM Expense e WHERE e.status = 1 AND e.payer = :userHandle")
    List<Expense> getPendingDebitTxns(String userHandle);
}
