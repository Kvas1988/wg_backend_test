package com.example.wg_backend.controller;

import com.example.wg_backend.model.Cat;
import com.example.wg_backend.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {

    @Autowired
    private CatRepository catRepository;

    @GetMapping(path = "")
    public List<Cat> getAll() {
        return catRepository.findAll();
    }
}
