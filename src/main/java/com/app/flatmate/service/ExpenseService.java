package com.app.flatmate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.app.flatmate.model.Expense;

@Service
public class ExpenseService {

    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense e) {
        expenses.add(e);
    }

    public List<Expense> getAll() {
        return expenses;
    }

    public double getTotal() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    // ✅ CLEAN BALANCE CALCULATION
    public Map<String, Double> calculateBalances() {

        Map<String, Double> paid = new HashMap<>();

        for (Expense e : expenses) {
            paid.put(e.getPaidBy(),
                    paid.getOrDefault(e.getPaidBy(), 0.0) + e.getAmount());
        }

        double total = getTotal();
        int people = paid.size();

        if (people == 0) return new HashMap<>();

        double share = total / people;

        Map<String, Double> balance = new HashMap<>();

        for (String person : paid.keySet()) {
            double value = paid.get(person) - share;

            // round to 2 decimal
            value = Math.round(value * 100.0) / 100.0;

            balance.put(person, value);
        }

        return balance;
    }

    // ✅ HUMAN READABLE STATUS
    public Map<String, String> getStatus() {

        Map<String, Double> balance = calculateBalances();
        Map<String, String> status = new HashMap<>();

        for (String person : balance.keySet()) {

            double val = balance.get(person);

            if (val > 0) {
                status.put(person, "gets ₹" + val);
            } else if (val < 0) {
                status.put(person, "owes ₹" + Math.abs(val));
            } else {
                status.put(person, "settled");
            }
        }

        return status;
    }

    // ✅ SETTLEMENT (WHO PAYS WHOM)
    public List<String> settleUp() {

        Map<String, Double> balance = calculateBalances();

        List<Map.Entry<String, Double>> creditors = new ArrayList<>();
        List<Map.Entry<String, Double>> debtors = new ArrayList<>();

        for (Map.Entry<String, Double> e : balance.entrySet()) {
            if (e.getValue() > 0) creditors.add(e);
            else if (e.getValue() < 0) debtors.add(e);
        }

        List<String> result = new ArrayList<>();

        for (Map.Entry<String, Double> d : debtors) {

            double debt = -d.getValue();

            for (Map.Entry<String, Double> c : creditors) {

                double credit = c.getValue();

                if (debt <= 0) break;

                double settled = Math.min(debt, credit);

                if (settled > 0) {

                    result.add(d.getKey() + " pays " + c.getKey() + " ₹" + settled);

                    debt -= settled;
                    c.setValue(credit - settled);
                }
            }
        }

        return result;
    }
}