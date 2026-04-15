package com.app.flatmate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.flatmate.model.Flatmate;

@Service
public class FlatmateService {

    private List<Flatmate> flatmates = new ArrayList<>();

    public void addFlatmate(String name) {
        flatmates.add(new Flatmate(name));
    }

    public List<Flatmate> getAll() {
        return flatmates;
    }
}