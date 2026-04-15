package com.app.flatmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.flatmate.service.FlatmateService;

@Controller
public class FlatmateController {

    @Autowired
    private FlatmateService flatmateService;

    @GetMapping("/flatmates")
    public String flatmatesPage(Model model) {
        model.addAttribute("flatmates", flatmateService.getAll());
        return "flatmates";
    }

    @PostMapping("/addFlatmate")
    public String addFlatmate(@RequestParam String name) {
        flatmateService.addFlatmate(name);
        return "redirect:/flatmates";
    }
}