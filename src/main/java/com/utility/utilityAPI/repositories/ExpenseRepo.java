package com.utility.utilityAPI.repositories;

import com.utility.utilityAPI.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByPayer(String userName);
    List<Expense> findByPayee(String userName);
    List<Expense> findByPayeeAndTag(String Username, String tag);
    List<Expense> findByPayerAndTag(String userName, String tag);
}
