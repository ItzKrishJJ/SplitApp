package com.Jayesh.SplitApp.service;

import com.Jayesh.SplitApp.dto.BalanceResponse;
import com.Jayesh.SplitApp.dto.SettlementsResponse;
import com.Jayesh.SplitApp.model.Expense;
import com.Jayesh.SplitApp.model.ExpenseShare;
import com.Jayesh.SplitApp.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final ExpenseRepository expenseRepo;

    public List<BalanceResponse> calculateBalances() {
        Map<String, BigDecimal> paidMap = new HashMap<>();
        Map<String, BigDecimal> owedMap = new HashMap<>();

        List<Expense> expenses = expenseRepo.findAll();

        for (Expense expense : expenses) {
            String paidBy = expense.getPaidBy().getName();
            BigDecimal currentPaid = paidMap.getOrDefault(paidBy, BigDecimal.ZERO);
            paidMap.put(paidBy, currentPaid.add(expense.getAmount()));

            for (ExpenseShare share : expense.getShares()) {
                String person = share.getPerson().getName();
                BigDecimal currentOwed = owedMap.getOrDefault(person, BigDecimal.ZERO);
                owedMap.put(person, currentOwed.add(share.getValue()));
            }
        }

        Set<String> allPeople = new HashSet<>();
        allPeople.addAll(paidMap.keySet());
        allPeople.addAll(owedMap.keySet());

        List<BalanceResponse> result = new ArrayList<>();
        for (String person : allPeople) {
            BigDecimal paid = paidMap.getOrDefault(person, BigDecimal.ZERO);
            BigDecimal owed = owedMap.getOrDefault(person, BigDecimal.ZERO);
            BigDecimal balance = paid.subtract(owed);
            result.add(new BalanceResponse(person, balance));
        }

        return result;
    }

    public List<SettlementsResponse> getOptimizedSettlements() {
        List<BalanceResponse> balances = calculateBalances();

        PriorityQueue<BalanceResponse> positive = new PriorityQueue<>(
                (a, b) -> b.balance().compareTo(a.balance())
        );
        PriorityQueue<BalanceResponse> negative = new PriorityQueue<>(
                Comparator.comparing(BalanceResponse::balance)
        );

        for (BalanceResponse b : balances) {
            if (b.balance().compareTo(BigDecimal.ZERO) > 0) positive.offer(b);
            else if (b.balance().compareTo(BigDecimal.ZERO) < 0) negative.offer(b);
        }

        List<SettlementsResponse> result = new ArrayList<>();

        while (!positive.isEmpty() && !negative.isEmpty()) {
            BalanceResponse creditor = positive.poll();
            BalanceResponse debtor = negative.poll();

            BigDecimal amount = creditor.balance().min(debtor.balance().negate());

            result.add(new SettlementsResponse(debtor.name(), creditor.name(), amount));

            BigDecimal remainingCredit = creditor.balance().subtract(amount);
            BigDecimal remainingDebt = debtor.balance().add(amount);

            if (remainingCredit.compareTo(BigDecimal.ZERO) > 0) {
                positive.offer(new BalanceResponse(creditor.name(), remainingCredit));
            }
            if (remainingDebt.compareTo(BigDecimal.ZERO) < 0) {
                negative.offer(new BalanceResponse(debtor.name(), remainingDebt));
            }
        }

        return result;
    }
}
