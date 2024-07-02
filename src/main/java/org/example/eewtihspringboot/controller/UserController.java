package org.example.eewtihspringboot.controller;


import lombok.RequiredArgsConstructor;
import org.example.eewtihspringboot.repo.CategoryRepo;
import org.example.eewtihspringboot.repo.ProductRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        return "admin";
    }
}
