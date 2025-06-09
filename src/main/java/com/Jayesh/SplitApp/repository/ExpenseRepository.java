package com.Jayesh.SplitApp.repository;

import com.Jayesh.SplitApp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
