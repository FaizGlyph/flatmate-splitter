package com.app.flatmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.flatmate.service.ExpenseService;

@Controller
public class BalanceController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/balances")
    public String balancesPage(Model model) {

        model.addAttribute("status", expenseService.getStatus());
        model.addAttribute("settlements", expenseService.settleUp());

        return "balances";
    }
}