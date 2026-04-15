package com.app.flatmate.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public boolean login(String username, String password) {
        return username.equals("admin") && password.equals("123");
    }
}