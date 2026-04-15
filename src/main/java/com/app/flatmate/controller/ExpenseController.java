package com.app.flatmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.flatmate.model.Expense;
import com.app.flatmate.service.ExpenseService;
import com.app.flatmate.service.FlatmateService;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private FlatmateService flatmateService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("expenses", expenseService.getAll());
        model.addAttribute("total", expenseService.getTotal());

        model.addAttribute("balances", expenseService.calculateBalances());
        model.addAttribute("status", expenseService.getStatus());
        model.addAttribute("settlements", expenseService.settleUp());

        // ✅ NEW
        model.addAttribute("flatmates", flatmateService.getAll());
        model.addAttribute("flatmateCount", flatmateService.getAll().size());

        return "dashboard";
    }

    @PostMapping("/addExpense")
    public String addExpense(@RequestParam String description,
                            @RequestParam double amount,
                            @RequestParam String paidBy) {

        Expense e = new Expense();
        e.setDescription(description);
        e.setAmount(amount);
        e.setPaidBy(paidBy);

        expenseService.addExpense(e);

        return "redirect:/dashboard";
    }
}