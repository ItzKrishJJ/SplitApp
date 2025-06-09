package com.Jayesh.SplitApp.service;

import com.Jayesh.SplitApp.dto.ExpenseRequest;
import com.Jayesh.SplitApp.model.*;
import com.Jayesh.SplitApp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepo;
    private final PersonRepository personRepo;
    private final ExpenseShareRepository shareRepo;

    @Transactional
    public Expense createExpense(ExpenseRequest request) {
        Person paidBy = personRepo.findByName(request.getPaidBy())
                .orElseGet(() -> personRepo.save(Person.builder().name(request.getPaidBy()).build()));

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .paidBy(paidBy)
                .build();

        expense = expenseRepo.save(expense);

        List<ExpenseShare> shares = new ArrayList<>();
        for (ExpenseRequest.ShareDetail shareDetail : request.getShares()) {
            Person person = personRepo.findByName(shareDetail.getName())
                    .orElseGet(() -> personRepo.save(Person.builder().name(shareDetail.getName()).build()));

            ExpenseShare share = ExpenseShare.builder()
                    .expense(expense)
                    .person(person)
                    .shareType(shareDetail.getShareType())
                    .value(shareDetail.getValue())
                    .build();
            shares.add(share);
        }

        shareRepo.saveAll(shares);
        expense.setShares(shares);
        return expense;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepo.findById(Math.toIntExact(id)).orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public void deleteExpense(Long id) {
        if (!expenseRepo.existsById(Math.toIntExact(id))) {
            throw new RuntimeException("Expense does not exist");
        }
        expenseRepo.deleteById(Math.toIntExact(id));
    }

    public Expense updateExpense(Long id, ExpenseRequest request) {
        Expense existing = getExpenseById(id);
        existing.setAmount(request.getAmount());
        existing.setDescription(request.getDescription());

        Person paidBy = personRepo.findByName(request.getPaidBy())
                .orElseGet(() -> personRepo.save(Person.builder().name(request.getPaidBy()).build()));
        existing.setPaidBy(paidBy);

        // Clear old shares and save new ones
        shareRepo.deleteAll(existing.getShares());

        List<ExpenseShare> updatedShares = new ArrayList<>();
        for (ExpenseRequest.ShareDetail detail : request.getShares()) {
            Person person = personRepo.findByName(detail.getName())
                    .orElseGet(() -> personRepo.save(Person.builder().name(detail.getName()).build()));

            ExpenseShare share = ExpenseShare.builder()
                    .expense(existing)
                    .person(person)
                    .shareType(detail.getShareType())
                    .value(detail.getValue())
                    .build();

            updatedShares.add(share);
        }

        shareRepo.saveAll(updatedShares);
        existing.setShares(updatedShares);
        return expenseRepo.save(existing);
    }
}
