package com.utility.utilityAPI.repositories;

import com.utility.utilityAPI.models.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
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
}
